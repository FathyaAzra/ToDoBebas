package com.example.todobebas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity(), RepeatSettingsDialogFragment.OnRepeatChangeListener {

    private var selectedDate: Long = System.currentTimeMillis() // To store the selected date
    private var todoId: Int = -1 // Global task ID
    private var repeatInterval: String? = null // Store the repeat interval

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
        val todoDate = intent.getLongExtra("todoDate", 0L)

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

        //button atur Waktu
        val btnSetTime: Button = findViewById(R.id.btnSetTime)
        btnSetTime.setOnClickListener {
            val timePickerFragment = TimePickerDialogFragment { date, hour, minute ->
                // Tangani data yang dipilih
                println("Tanggal: $date, Waktu: $hour:${String.format("%02d", minute)}")
            }
            timePickerFragment.show(supportFragmentManager, "TimePickerDialogFragment")
        }

        // Save button logic
        val buttonSimpan: Button = findViewById(R.id.button_simpan)
        buttonSimpan.setOnClickListener {
            val updatedName = namaTask.text.toString().trim()
            val updatedDesc = descTask.text.toString().trim()

            if (todoId != -1 && updatedName.isNotEmpty() && updatedDesc.isNotEmpty()) {
                val updatedTodo = Todo(
                    todo_id = todoId,
                    todo_name = updatedName,
                    todo_desc = updatedDesc,
                    todo_date = selectedDate,
                    todo_status = "not yet" // Default status
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
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) // Define the desired format
        return formatter.format(Date(timestamp)) // Format the timestamp into a string
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
                todo_status = "not yet"
            )

            // Insert the repeated task into the database
            CoroutineScope(Dispatchers.IO).launch {
                val database = AppDatabase.getDatabase(applicationContext)
                database.todoDao().insertAll(repeatedTodo)
            }
        }
    }

    override fun onRepeatChanged(status: Boolean, repeatInterval: String?) {
        // Handle repeat change
        this
    }
}
