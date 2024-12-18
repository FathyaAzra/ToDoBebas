package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity(), RepeatSettingsDialogFragment.OnRepeatChangeListener {

    private var selectedDate: Long = System.currentTimeMillis()
    private var todoId: Int = -1
    private var repeatInterval: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

        // Retrieve UI components
        val namaTask: EditText = findViewById(R.id.namaTask)
        val descTask: EditText = findViewById(R.id.descTask)
        val tvSelectedTime: TextView = findViewById(R.id.tv_selected_time)

        // Fetch Intent data
        todoId = intent.getIntExtra("todoId", -1)
        val todoName = intent.getStringExtra("todoName") ?: ""
        val todoDesc = intent.getStringExtra("todoDesc") ?: ""
        val todoDate = intent.getLongExtra("todoDate", System.currentTimeMillis())

        // Set initial data in UI
        if (todoId != -1) {
            namaTask.setText(todoName)
            descTask.setText(todoDesc)
            selectedDate = todoDate
            tvSelectedTime.text = formatDate(todoDate)
        }

        // Back button logic
        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        // Button atur Waktu
        val btnSetTime: Button = findViewById(R.id.btnSetTime)
        btnSetTime.setOnClickListener {
            val timePickerFragment = TimePickerDialogFragment { selectedDateStr, selectedHour, selectedMinute ->
                // Parsing the selected date from string to timestamp
                val dateParts = selectedDateStr.split("/")
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, dateParts[0].toInt())
                calendar.set(Calendar.MONTH, dateParts[1].toInt() - 1) // Month starts from 0
                calendar.set(Calendar.DAY_OF_MONTH, dateParts[2].toInt())

                // Set hour and minute
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)

                // Update the selected timestamp
                selectedDate = calendar.timeInMillis

                // Update the displayed time
                tvSelectedTime.text = formatDate(selectedDate)
            }

            timePickerFragment.show(supportFragmentManager, "TimePickerDialogFragment")
        }

        // Save button logic
        val buttonSimpan: Button = findViewById(R.id.button_simpan)
        buttonSimpan.setOnClickListener {
            val updatedName = namaTask.text.toString().trim()
            val updatedDesc = descTask.text.toString().trim()

            if (todoId != -1 && updatedName.isNotEmpty()) {
                val updatedTodo = Todo(
                    todo_id = todoId,
                    todo_name = updatedName,
                    todo_desc = updatedDesc,
                    todo_date = selectedDate,
                    todo_status = "not yet", // Default status
                    hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), // Default hour if needed
                    minute = Calendar.getInstance().get(Calendar.MINUTE) // Default minute if needed
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val database = AppDatabase.getDatabase(applicationContext)
                    database.todoDao().update(updatedTodo)

                    // Handle repeat tasks if selected
                    if (repeatInterval != null) {
                        repeatTasks(updatedTodo)
                    }

                    withContext(Dispatchers.Main) {
                        // Create an intent to send back the updated data
                        val resultIntent = Intent().apply {
                            putExtra("updatedTodoId", updatedTodo.todo_id)
                            putExtra("updatedTodoName", updatedTodo.todo_name)
                            putExtra("updatedTodoDesc", updatedTodo.todo_desc)
                            putExtra("updatedTodoDate", updatedTodo.todo_date)
                        }
                        setResult(RESULT_OK, resultIntent) // Set the result with updated data

                        // Show success message
                        Toast.makeText(this@EditActivity, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show()

                        finish() // Finish the EditActivity and return to TugasActivity
                    }
                }
            } else {
                Toast.makeText(this, "Error: Task ID not found or fields are empty.", Toast.LENGTH_SHORT).show()
            }
        }



        // Repeat Settings Button
        val buttonMengulang: Button = findViewById(R.id.btnMengulang)
        buttonMengulang.setOnClickListener {
            val dialog = RepeatSettingsDialogFragment()
            dialog.show(supportFragmentManager, "RepeatSettingsDialog")
        }
    }

    private fun formatDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    private fun repeatTasks(originalTodo: Todo) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = originalTodo.todo_date

        val repeatCount = 5 // For example, create 5 repeated tasks, adjust as necessary
        for (i in 1..repeatCount) {
            val newDate = when (repeatInterval) {
                "daily" -> calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis
                "weekly" -> calendar.apply { add(Calendar.WEEK_OF_YEAR, 1) }.timeInMillis
                "monthly" -> calendar.apply { add(Calendar.MONTH, 1) }.timeInMillis
                "yearly" -> calendar.apply { add(Calendar.YEAR, 1) }.timeInMillis
                else -> calendar.timeInMillis
            }

            val repeatedTodo = Todo(
                todo_id = 0, // Let the database assign a new ID
                todo_name = originalTodo.todo_name,
                todo_desc = originalTodo.todo_desc,
                todo_date = newDate,
                todo_status = "not yet", // Default status
                hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), // Default hour if needed
                minute = Calendar.getInstance().get(Calendar.MINUTE) // Default minute if needed
            )

            // Insert the repeated task into the database
            CoroutineScope(Dispatchers.IO).launch {
                val database = AppDatabase.getDatabase(applicationContext)
                database.todoDao().insertAll(repeatedTodo)
            }
        }
    }


    override fun onRepeatChanged(status: Boolean, repeatInterval: String?) {
        if (status) {
            this.repeatInterval = repeatInterval
        } else {
            this.repeatInterval = null
        }
    }
}

