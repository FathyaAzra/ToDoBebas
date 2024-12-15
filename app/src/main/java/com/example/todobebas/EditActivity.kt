package com.example.todobebas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class EditActivity : AppCompatActivity(), RepeatSettingsDialogFragment.OnRepeatChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

        //menulis nama tugas
        val namaTask : EditText = findViewById(R.id.namaTask)

        //gambar back
        val backArrow : ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener { onClick(it) }

        //tombol atur waktu
        val buttonSetTime: Button = findViewById(R.id.btnSetTime)
        buttonSetTime.setOnClickListener {
            val timePickerDialog = TimePickerDialogFragment { hour, minute ->
                // Logika untuk menangani waktu yang dipilih
            }
            timePickerDialog.show(supportFragmentManager, "TimePickerDialog")
        }

        //tombol atur pengingat
        val buttonPengingat : Button = findViewById(R.id.btnPengingat)
        val buttonContainer: LinearLayout = findViewById(R.id.button_container)
        buttonPengingat.setOnClickListener {
                val newButton1 = Button(this)
                val newButton2 = Button(this)
                val newButton3 = Button(this)
                newButton1.text = "3 Jam Sebelumnya"
                newButton2.text = "1 Hari Sebelumnya"
                newButton3.text = "1 Minggu Sebelumnya"

                newButton1.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                buttonContainer.addView(newButton1)
                newButton2.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                buttonContainer.addView(newButton2)
                newButton3.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                buttonContainer.addView(newButton3)

            // Nonaktifkan tombol utama setelah menambahkan tombol
            buttonPengingat.visibility = View.GONE
        }

        // tombol mengulang tugas
        val buttonMengulang : Button = findViewById(R.id.btnMengulang)
        buttonMengulang.setOnClickListener {
            val dialog = RepeatSettingsDialogFragment()
            dialog.show(supportFragmentManager, "RepeatSettingsDialog")
        }

        //tombol catatan
        val buttonCatatan : Button = findViewById(R.id.btnCatatan)
        buttonCatatan.setOnClickListener{ onClick(it) }

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
            R.id.btnCatatan -> {
                val intent = Intent(this, catatanTask::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onRepeatChanged(status: Boolean) {
        val repeatButton: Button = findViewById(R.id.btnMengulang)
        repeatButton.text = if (status) "Ya" else "Tidak"
    }


}