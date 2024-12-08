package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class catatanTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catatan_task)

        val BackArrow : ImageView = findViewById(R.id.backArrow)
        BackArrow.setOnClickListener { onClick(it) }

        val buttonSimpan : Button = findViewById(R.id.button_simpan)
        buttonSimpan.setOnClickListener{ onClick(it) }

        val EditCatatan : EditText = findViewById(R.id.editCatatan)
        EditCatatan.requestFocus()

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(EditCatatan, InputMethodManager.SHOW_IMPLICIT)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.backArrow -> {
                    val intent = Intent(this, EditActivity::class.java)
                    startActivity(intent)
            }
        }
    }

}