package com.example.todobebas


import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar


class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.edit_activity)

        val namaTask : EditText = findViewById(R.id.namaTask)
        val buttonCatatan : Button = findViewById(R.id.btnCatatan)


        //gambar back
        val backArrow : ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener { onClick(it) }

        //tombol atur waktu
        val btnSetTime: Button = findViewById(R.id.btnSetTime)
        val tvSelectedTime: TextView = findViewById(R.id.tv_selected_time)
        btnSetTime.setOnClickListener {
            val timePickerDialog = TimePickerDialogFragment { hour, minute ->
                // Set selected time to TextView
                tvSelectedTime.text = String.format("%02d:%02d", hour, minute)
            }
            timePickerDialog.show(supportFragmentManager, "TimePickerDialog")
        }

        //tombol atur pengingat
        val buttonPengingat : Button = findViewById(R.id.btnPengingat)
        val buttonContainer: LinearLayout = findViewById(R.id.button_container)
        buttonPengingat.setOnClickListener {
            for (i in 1..3) {
                val newButton = Button(this)
                newButton.text = "Tombol $i"
                newButton.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                buttonContainer.addView(newButton)
            }

            // Nonaktifkan tombol utama setelah menambahkan tombol
            buttonPengingat.visibility = View.GONE
        }

        val buttonMengulang : Button = findViewById(R.id.btnMengulang)
        buttonMengulang.setOnClickListener {
            val dialog = RepeatSettingsDialogFragment()
            dialog.show(supportFragmentManager, "RepeatSettingsDialog")
        }


        //tombol simpan
        val buttonSimpan : Button = findViewById(R.id.button_simpan)
        buttonSimpan.setOnClickListener {


            Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }


    private fun onClick(view: View) {
        when (view.id) {
            R.id.backArrow -> {
                val intent = Intent(this, TugasActivity::class.java)
                startActivity(intent)
            }

        }
    }


}