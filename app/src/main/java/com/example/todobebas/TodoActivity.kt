package com.example.todobebas

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import android.view.View

class TodoActivity : ComponentActivity() {
    private lateinit var button2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_todo)

        button2 = findViewById(R.id.btnmasuk) // Make sure this ID exists in your XML
        button2.setOnClickListener { onClick(it) }

    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.btnmasuk -> {
                val intent = Intent(this, EditActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

