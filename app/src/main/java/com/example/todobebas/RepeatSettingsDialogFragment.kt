package com.example.todobebas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

class RepeatSettingsDialogFragment : DialogFragment() {

    interface OnRepeatChangeListener {
        fun onRepeatChanged(status: Boolean)
    }

    private var listener: OnRepeatChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_repeat, container, false)

        val switchRepeat: Switch = view.findViewById(R.id.switch_repeat)

        val buttons = listOf(
            view.findViewById<Button>(R.id.btn_day),
            view.findViewById<Button>(R.id.btn_week),
            view.findViewById<Button>(R.id.btn_month),
            view.findViewById<Button>(R.id.btn_year)
        )
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnDone: Button = view.findViewById(R.id.btn_done)

        val colorOn = ContextCompat.getColor(requireContext(), R.color.lightblue)
        val colorOff = ContextCompat.getColor(requireContext(), R.color.grey)

        updateButtonState(switchRepeat.isChecked, buttons, colorOn, colorOff)

        switchRepeat.setOnCheckedChangeListener { _, isChecked ->
            updateButtonState(isChecked, buttons, colorOn, colorOff)

            listener?.onRepeatChanged(isChecked)
        }

        btnCancel.setOnClickListener { dismiss() }
        btnDone.setOnClickListener { dismiss() }

        buttons.forEach { button ->
            button.setOnClickListener {
                highlightSelectedButton(button, buttons, colorOn, colorOff)
            }
        }

        return view
    }

    private fun updateButtonState(
        isChecked: Boolean,
        buttons: List<Button>,
        colorOn: Int,
        colorOff: Int
    ) {
        buttons.forEach { button ->
            button.setBackgroundColor(if (isChecked) colorOn else colorOff)
            button.isEnabled = isChecked
        }
    }

    private fun highlightSelectedButton(
        selectedButton: Button,
        buttons: List<Button>,
        colorOn: Int,
        colorOff: Int
    ) {
        buttons.forEach { button ->
            button.setBackgroundColor(if (button == selectedButton) colorOn else colorOff)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRepeatChangeListener) {
            listener = context
        } else {
            throw RuntimeException("$context harus mengimplementasi OnRepeatChangeListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}
