package com.example.todobebas

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.R
//import com.example.todobebas.setOnClickListener
//import com.example.todobebas.setText
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {
    private lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        button = findViewById(R.id.btnSaveTask) // Make sure this ID exists in your XML
        button.setOnClickListener { onClick(it) }

//        var edTaskDate = null
////        edTaskDate.setOnClickListener {
////            showDatePicker()
////        }
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
//                edTaskDate.setText(formattedDate)
            }, year, month, day)

        datePickerDialog.show()
    }
}