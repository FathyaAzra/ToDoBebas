package com.example.todobebas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.DialogFragment
import com.example.todobebas.R
import android.content.Context;
import androidx.core.content.ContextCompat;


class RepeatSettingsDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_repeat, container, false)

        val switchRepeat: Switch = view.findViewById(R.id.switch_repeat)
        val btnDay : Button = view.findViewById(R.id.btn_day)
        val btnWeek : Button = view.findViewById(R.id.btn_week)
        val btnMonth : Button = view.findViewById(R.id.btn_month)
        val btnYear : Button = view.findViewById(R.id.btn_year)

//        val int colorOn = ContextCompat.getColor(this, R.color.lightblue)
//        val int colorOff = ContextCompat.getColor(this, R.color.grey)

        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnDone: Button = view.findViewById(R.id.btn_done)

        switchRepeat.setOnCheckedChangeListener { _, isChecked ->
//            btnDay.setBackgroundColor(colorOn)
//            btnWeek.setBackgroundColor(Color.Blue)
//            btnMonth.setBackgroundColor(Color.Blue)
//            btnYear.setBackgroundColor(Color.Blue)
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

