package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button // Make sure to import Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity

fun onClick(view: View){
    when (view.id){
        R.id.btnmasuk -> {
            val intent  = Intent(this, TodoActivity::class.java)
            startActivity(intent)
        }
    }
}
class MainActivity : AppCompatActivity() {
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge for immersive experience
        setContentView(R.layout.activity_main)
    }
}
