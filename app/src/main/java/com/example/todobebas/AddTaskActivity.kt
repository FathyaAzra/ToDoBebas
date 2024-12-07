package com.example.todobebas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.databinding.ActivityAddTaskBinding
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var button: Button

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = findViewById(R.id.btnSaveTask) // Make sure this ID exists in your XML
        button.setOnClickListener { onClick(it) }

        // Menambahkan listener pada ImageView untuk berpindah ke TugasActivity
        binding.closeImg.setOnClickListener {
            val intent = Intent(this, TugasActivity::class.java)
            startActivity(intent)
            finish() // Opsional: Menutup AddTaskActivity agar tidak kembali ke sini
        }

        // Set OnClickListener pada edTaskDate
        binding.edTaskDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.btnSaveTask -> {
                val intent = Intent(this, TugasActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun showDatePicker() {
        // Dapatkan tanggal sekarang
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Tampilkan DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format tanggal (misalnya: 2024-12-01)
                val selectedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                // Set teks pada edTaskDate
                binding.edTaskDate.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
