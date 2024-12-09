package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.databinding.ActivityTugasBinding

class TugasActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var binding: ActivityTugasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tugas)
        binding = ActivityTugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = findViewById(R.id.btnTambah) // Make sure this ID exists in your XML
        button.setOnClickListener { onClick(it) }

        // Listener untuk navigasi ke KalenderActivity
        binding.navKalender.setOnClickListener {
            val intent = Intent(this, KalenderActivity::class.java)
            startActivity(intent)
        }

        // Listener untuk navigasi ke StatistikActivity
        binding.navStatistik.setOnClickListener {
            val intent = Intent(this, StatistikActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.btnTambah -> {
                val intent = Intent(this, EditActivity::class.java)
                startActivity(intent)
            }

        }
    }
}