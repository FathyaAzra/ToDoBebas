package com.example.todobebas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.DialogFragment
import com.example.todobebas.R

class RepeatSettingsDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_repeat, container, false)

        val switchRepeat: Switch = view.findViewById(R.id.switch_repeat)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnDone: Button = view.findViewById(R.id.btn_done)

        // Toggle repeat options
        switchRepeat.setOnCheckedChangeListener { _, isChecked ->
            // Handle toggle logic here
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

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}