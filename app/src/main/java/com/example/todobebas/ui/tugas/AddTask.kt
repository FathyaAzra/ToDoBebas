package com.example.todobebas.ui.tugas

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.R
import com.example.todobebas.setOnClickListener
import com.example.todobebas.setText
import java.util.Calendar

class AddTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task_dialog)

        var edTaskDate = null
        edTaskDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // Format tanggal dan tampilkan di TextInputEditText
                val formattedDate =
                    String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
                var edTaskDate = null
                edTaskDate.setText(formattedDate)
            }, year, month, day)

        datePickerDialog.show()
    }
}