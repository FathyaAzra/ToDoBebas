package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Catatan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatatanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catatan_task)

        // Initialize views
        val editCatatan: EditText = findViewById(R.id.editCatatan)
        val buttonSimpan: Button = findViewById(R.id.button_simpan)
        val backArrow: ImageView = findViewById(R.id.backArrow)

        // Back arrow click listener to finish activity
        backArrow.setOnClickListener { finish() }

        // Save button click listener to insert the note into the database
        buttonSimpan.setOnClickListener {
            val noteText = editCatatan.text.toString().trim()  // Trim spaces
            val taskId = intent.getIntExtra("taskId", -1)  // Retrieve taskId from intent

            // Validate note text and task ID
            if (noteText.isNotEmpty() && taskId != -1) {
                // Start a coroutine for database interaction
                CoroutineScope(Dispatchers.IO).launch {
                    val database = AppDatabase.getDatabase(applicationContext)

                    val todoExists = database.todoDao().getTodoById(taskId) != null

                    if (todoExists) {
                        val newCatatan = Catatan(task_id = taskId, catatan = noteText)

                        // Insert the Catatan into the database
                        database.catatanDao().insert(newCatatan)

                        // Switch to the main thread to show Toast and update UI
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@CatatanActivity, "Catatan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                            editCatatan.text.clear()  // Clear the EditText
                            hideKeyboard(editCatatan)  // Hide the keyboard
                            finish()  // Finish the activity and go back to previous screen
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@CatatanActivity, "Task ID tidak valid!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                // If note text is empty or task ID is invalid, show an error
                Toast.makeText(this, "Catatan tidak boleh kosong atau Task ID tidak valid!", Toast.LENGTH_SHORT).show()
            }
        }

        // Request focus for the EditText and show the keyboard
        editCatatan.requestFocus()
        showKeyboard(editCatatan)
    }

    // Helper function to show the keyboard
    private fun showKeyboard(view: EditText) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    // Helper function to hide the keyboard
    private fun hideKeyboard(view: EditText) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
