package com.example.todobebas

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.todobebas.R
import java.util.Calendar
import android.app.DatePickerDialog

class TimePickerDialogFragment(private val onTimeSelected: (Int, Int) -> Unit) : DialogFragment() {

    private lateinit var edTaskDate: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_time_picker, container, false)

        val hourPicker: NumberPicker = view.findViewById(R.id.np_hour)
        val minutePicker: NumberPicker = view.findViewById(R.id.np_minute)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnDone: Button = view.findViewById(R.id.btn_done)
        edTaskDate = view.findViewById(R.id.edTaskDate)

        // Set NumberPicker values
        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        minutePicker.minValue = 0
        minutePicker.maxValue = 59

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnDone.setOnClickListener {
            onTimeSelected(hourPicker.value, minutePicker.value)
            dismiss()
        }

        // Set up click listener for the date picker EditText
        edTaskDate.setOnClickListener {
            showDatePicker { selectedDate ->
                edTaskDate.setText(selectedDate)
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    // Method to show DatePicker
    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        // Get current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Show DatePickerDialog
        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the date (e.g., 2024-12-01)
                val selectedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                onDateSelected(selectedDate)
            },
            year,
            month,
            day
        ).show()
    }
}
