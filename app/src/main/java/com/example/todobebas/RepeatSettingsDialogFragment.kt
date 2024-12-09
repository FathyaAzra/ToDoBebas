package com.example.todobebas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.DialogFragment
import androidx.core.content.ContextCompat
import com.example.todobebas.R

class RepeatSettingsDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_repeat, container, false)

        val switchRepeat: Switch = view.findViewById(R.id.switch_repeat)
        val btnDay: Button = view.findViewById(R.id.btn_day)
        val btnWeek: Button = view.findViewById(R.id.btn_week)
        val btnMonth: Button = view.findViewById(R.id.btn_month)
        val btnYear: Button = view.findViewById(R.id.btn_year)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnDone: Button = view.findViewById(R.id.btn_done)

        // Ambil warna dari sumber daya
        val colorOn = ContextCompat.getColor(requireContext(), R.color.lightblue)
        val colorOff = ContextCompat.getColor(requireContext(), R.color.grey)

        // Inisialisasi status awal tombol
        updateButtonState(switchRepeat.isChecked, btnDay, btnWeek, btnMonth, btnYear, colorOn, colorOff)

        // Atur listener untuk switch
        switchRepeat.setOnCheckedChangeListener { _, isChecked ->
            updateButtonState(isChecked, btnDay, btnWeek, btnMonth, btnYear, colorOn, colorOff)
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnDone.setOnClickListener {
            // Handle "Selesai" button logic here
            dismiss()
        }

        return view
    }

    private fun updateButtonState(
        isChecked: Boolean,
        btnDay: Button,
        btnWeek: Button,
        btnMonth: Button,
        btnYear: Button,
        colorOn: Int,
        colorOff: Int
    ) {
        if (isChecked) {
            // Ubah warna tombol dan aktifkan tombol saat switch diaktifkan
            btnDay.setBackgroundColor(colorOn)
            btnDay.isEnabled = true

            btnWeek.setBackgroundColor(colorOn)
            btnWeek.isEnabled = true

            btnMonth.setBackgroundColor(colorOn)
            btnMonth.isEnabled = true

            btnYear.setBackgroundColor(colorOn)
            btnYear.isEnabled = true
        } else {
            // Ubah warna tombol dan nonaktifkan tombol saat switch dinonaktifkan
            btnDay.setBackgroundColor(colorOff)
            btnDay.isEnabled = false

            btnWeek.setBackgroundColor(colorOff)
            btnWeek.isEnabled = false

            btnMonth.setBackgroundColor(colorOff)
            btnMonth.isEnabled = false

            btnYear.setBackgroundColor(colorOff)
            btnYear.isEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
