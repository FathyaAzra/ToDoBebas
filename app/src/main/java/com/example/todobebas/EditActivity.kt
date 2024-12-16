package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

        // Retrieve data from Intent
        val todoId = intent.getIntExtra("todoId", -1)
        val todoName = intent.getStringExtra("todoName")
        val todoDate = intent.getLongExtra("todoDate", 0L)

        // Initialize UI components
        val namaTask: EditText = findViewById(R.id.namaTask)
        val tvSelectedTime: TextView = findViewById(R.id.tv_selected_time)

        // Set initial values if todo data is available
        if (todoId != -1) {
            namaTask.setText(todoName)
            tvSelectedTime.text = formatDate(todoDate) // Set the formatted date
        }

        // Handle back button
        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            val intent = Intent(this, TugasActivity::class.java)
            startActivity(intent)
        }

        // Save button logic
        val buttonSimpan: Button = findViewById(R.id.button_simpan)
        buttonSimpan.setOnClickListener {
            val updatedName = namaTask.text.toString()
            val updatedDesc = namaTask.text.toString()  // Update description (assuming it's the same for now)
            val updatedDate = System.currentTimeMillis() // Replace with the selected date logic if needed

            if (todoId != -1) {
                val updatedTodo = Todo(
                    todo_id = todoId,
                    todo_name = updatedName,
                    todo_desc = updatedDesc,
                    todo_date = updatedDate,
                    todo_status = "not yet" // Update this if status is changeable
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val database = AppDatabase.getDatabase(applicationContext)
                    database.todoDao().update(updatedTodo)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditActivity, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity after saving
                    }
                }
            } else {
                Toast.makeText(this, "Error: Task ID not found.", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle setting reminder buttons
        val buttonPengingat: Button = findViewById(R.id.btnPengingat)
        val buttonContainer: LinearLayout = findViewById(R.id.button_container)
        buttonPengingat.setOnClickListener {
            val newButton1 = Button(this)
            val newButton2 = Button(this)
            val newButton3 = Button(this)
            newButton1.text = "3 Jam Sebelumnya"
            newButton2.text = "1 Hari Sebelumnya"
            newButton3.text = "1 Minggu Sebelumnya"

            newButton1.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            buttonContainer.addView(newButton1)
            newButton2.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            buttonContainer.addView(newButton2)
            newButton3.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            buttonContainer.addView(newButton3)

            // Hide main button after adding new buttons
            buttonPengingat.visibility = View.GONE
        }

        // Repeat button logic
        val buttonMengulang: Button = findViewById(R.id.btnMengulang)
        buttonMengulang.setOnClickListener {
            val dialog = RepeatSettingsDialogFragment()
            dialog.show(supportFragmentManager, "RepeatSettingsDialog")
        }

        // Handle window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Helper function to format date
    private fun formatDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    override fun onRepeatChanged(status: Boolean) {
        val repeatButton: Button = findViewById(R.id.btnMengulang)
        repeatButton.text = if (status) "Ya" else "Tidak"
    }
}
