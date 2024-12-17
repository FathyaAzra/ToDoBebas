package com.example.todobebas

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class TimePickerDialogFragment(private val onTimeSelected: (String, Int, Int) -> Unit) : DialogFragment() {

    private lateinit var edTaskDate: TextInputEditText
    private lateinit var npHour: NumberPicker
    private lateinit var npMinute: NumberPicker
    private lateinit var btnCancel: Button
    private lateinit var btnDone: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_time_picker, container, false)

        // Hubungkan komponen layout dengan variabel
        edTaskDate = view.findViewById(R.id.edTaskDate)
        npHour = view.findViewById(R.id.np_hour)
        npMinute = view.findViewById(R.id.np_minute)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnDone = view.findViewById(R.id.btn_done)

        // Konfigurasi NumberPicker untuk Jam (0 - 23)
        npHour.minValue = 0
        npHour.maxValue = 23

        // Konfigurasi NumberPicker untuk Menit (0 - 59)
        npMinute.minValue = 0
        npMinute.maxValue = 59

        // Atur input tanggal menggunakan DatePickerDialog
        edTaskDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d/%02d/%02d", selectedYear, selectedMonth + 1, selectedDay)
                edTaskDate.setText(formattedDate)
            }, year, month, day)

            datePicker.show()
        }

        // Tombol "Batal" untuk menutup dialog
        btnCancel.setOnClickListener {
            dismiss() // Menutup dialog
        }

        // Tombol "Selesai" untuk mengambil nilai dari input
        btnDone.setOnClickListener {
            val selectedHour = npHour.value
            val selectedMinute = npMinute.value
            val selectedDate = edTaskDate.text.toString()

            // Kirim data kembali melalui callback
            onTimeSelected(selectedDate, selectedHour, selectedMinute)

            // Tutup dialog
            dismiss()
        }

        return view
    }
}
