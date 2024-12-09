package com.example.todobebas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import com.example.todobebas.databinding.ActivityAddTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveTask.setOnClickListener {
            saveTaskToDatabase()
        }

        binding.closeImg.setOnClickListener {
            val intent = Intent(this, TugasActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.edTaskDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                binding.edTaskDate.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun convertDateToTimestamp(date: String): Long {
        val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val parsedDate = dateFormat.parse(date)
            parsedDate?.time ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    private fun saveTaskToDatabase() {
        val title = binding.edTaskTitle.text.toString()
        val date = binding.edTaskDate.text.toString()

        // Validasi input
        if (title.isEmpty()) {
            binding.edTaskTitleL.error = "Judul tugas tidak boleh kosong"
            return
        }

        if (date.isEmpty()) {
            binding.edTaskDateL.error = "Tanggal tidak boleh kosong"
            return
        }

        // Konversi tanggal ke timestamp
        val dateTimestamp = convertDateToTimestamp(date)

        // Buat objek Todo
        val todo = Todo(
            todo_id = 0,
            todo_name = title,
            todo_desc = "Default Description", // Sesuaikan jika ada deskripsi dari user
            todo_date = dateTimestamp
        )

        // Simpan ke database
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().insertAll(todo)

            // Mendapatkan semua data dari database untuk verifikasi
            val todos = database.todoDao().getAll()
            for (todoItem in todos) {
                println("Todo: ${todoItem.todo_name}, Date: ${todoItem.todo_date}")
            }
        }

        // Pindah ke TugasActivity setelah menyimpan
        val intent = Intent(this, TugasActivity::class.java)
        startActivity(intent)
        finish()
    }
}
