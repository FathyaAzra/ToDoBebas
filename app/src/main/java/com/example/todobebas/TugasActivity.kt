package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.TodoAdapter
import com.example.todobebas.databinding.ActivityTugasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TugasActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var binding: ActivityTugasBinding
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tugas)

        // Inisialisasi RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.taskRV)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi adapter
        adapter = TodoAdapter()
        recyclerView.adapter = adapter

        // Muat data dari database
        loadTodos()

        binding = ActivityTugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = findViewById(R.id.btnTambah)
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

    private fun loadTodos() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            val todos = database.todoDao().getAll()

            // Kirim data ke UI thread
            withContext(Dispatchers.Main) {
                adapter.setTodos(todos)  // Pastikan tipe data sama
                for (todoItem in todos) {
                    println("Todo: ${todoItem.todo_name}, Date: ${todoItem.todo_date}")
                }
            }
        }
    }
}