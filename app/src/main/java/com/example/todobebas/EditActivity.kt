package com.example.todobebas


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.edit_activity)

        val backArrow : ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener { onClick(it) }

        val buttonSimpan : Button = findViewById(R.id.button_simpan)
        val namaTask : EditText = findViewById(R.id.namaTask)
        val buttonWaktu : Button = findViewById(R.id.btnWaktu)
        val buttonPengingat : Button = findViewById(R.id.btnPengingat)
        val buttonContainer: LinearLayout = findViewById(R.id.button_container)
        val buttonMengulang : Button = findViewById(R.id.btnMengulang)
        val buttonCatatan : Button = findViewById(R.id.btnCatatan)

        buttonSimpan.setOnClickListener {
            val editValue = namaTask.text.toString()
            val drawableId = backArrow.drawable.constantState

        }

        Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        buttonPengingat.setOnClickListener {
            // Buat tiga tombol baru
            for (i in 1..3) {
                val newButton = Button(this)
                newButton.text = "Tombol $i"
                newButton.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                // Set OnClickListener untuk tombol baru
                newButton.setOnClickListener {
                    Toast.makeText(this, "Tombol $i diklik", Toast.LENGTH_SHORT).show()
                }

                // Tambahkan tombol ke container
                buttonContainer.addView(newButton)
            }

            // Nonaktifkan tombol utama setelah menambahkan tombol
            buttonPengingat.visibility = View.GONE
        }
    }


    private fun onClick(view: View) {
        when (view.id) {
            R.id.backArrow -> {
                val intent = Intent(this, TodoActivity::class.java)
                startActivity(intent)
            }
            R.id.button_simpan -> {

            }
        }
    }


}