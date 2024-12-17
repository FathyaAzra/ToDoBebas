package com.example.todobebas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import com.example.todobebas.databinding.ActivityAddTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener untuk tombol Simpan
        binding.btnSaveTask.setOnClickListener {
            saveTaskToDatabase()
        }

        // Listener untuk tombol tutup
        binding.closeImg.setOnClickListener {
            navigateToTaskActivity()
        }

        // Listener untuk memilih tanggal
        binding.edTaskDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Dialog pemilih tanggal
        DatePickerDialog(
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
        ).show()
    }

    private fun convertDateToTimestamp(date: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            dateFormat.parse(date)?.time ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    private fun saveTaskToDatabase() {
        val title = binding.edTaskTitle.text.toString().trim()
        val description = binding.edTaskDesc.text.toString().trim()
        val date = binding.edTaskDate.text.toString().trim()

        // Validasi input
        if (title.isEmpty()) {
            binding.edTaskTitleL.error = "Judul tugas tidak boleh kosong"
            return
        } else {
            binding.edTaskTitleL.error = null
        }

        if (description.isEmpty()) {
            binding.edTaskDesc.error = "Deskripsi tugas tidak boleh kosong"
            return
        } else {
            binding.edTaskDesc.error = null
        }

        if (date.isEmpty()) {
            binding.edTaskDateL.error = "Tanggal tidak boleh kosong"
            return
        } else {
            binding.edTaskDateL.error = null
        }

        val dateTimestamp = convertDateToTimestamp(date)
        if (dateTimestamp == 0L) {
            binding.edTaskDateL.error = "Format tanggal tidak valid"
            return
        }

        // Create and save Todo
        val todo = Todo(
            todo_id = 0,
            todo_name = title,
            todo_desc = description,
            todo_date = dateTimestamp,
            todo_status = "not yet"
        )

        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().insertAll(todo)

            runOnUiThread {
                navigateToTaskActivity()
            }
        }
    }


    private fun navigateToTaskActivity() {
        val intent = Intent(this, TugasActivity::class.java)
        startActivity(intent)
        finish()
    }
}
