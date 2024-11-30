package com.example.todobebas

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.example.todobebas.R

class TimePickerDialogFragment(private val onTimeSelected: (Int, Int) -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_time_picker, container, false)

        val hourPicker: NumberPicker = view.findViewById(R.id.np_hour)
        val minutePicker: NumberPicker = view.findViewById(R.id.np_minute)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnDone: Button = view.findViewById(R.id.btn_done)

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
}