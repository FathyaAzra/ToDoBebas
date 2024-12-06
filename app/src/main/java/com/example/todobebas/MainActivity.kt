package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.ui.kalender.KalenderViewModel
import com.example.todobebas.ui.tugas.TugasFragment
import com.example.todobebas.ui.tugas.TugasViewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btnmasuk) // Make sure this ID exists in your XML
        button.setOnClickListener { onClick(it) }

    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.btnmasuk -> {
                val intent = Intent(this, TugasActivity::class.java)
                startActivity(intent)
            }

        }
    }
//    private fun onClick(view: View) {
//        when (view.id) {
//            R.id.btnmasuk -> {
//                // Gunakan FragmentTransaction untuk memuat fragment
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, TugasFragment()) // Pastikan ID sesuai dengan layout container Anda
//                    .addToBackStack(null) // Tambahkan ke back stack (opsional)
//                    .commit()
//            }
//        }
//    }
}