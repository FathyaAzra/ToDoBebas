package com.example.todobebas


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class EditActivity : AppCompatActivity() {
    private lateinit var BackArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.edit_activity)

        BackArrow = findViewById(R.id.backArrow)
        BackArrow.setOnClickListener { onClick(it) }
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.backArrow -> {
                val intent = Intent(this, TodoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}