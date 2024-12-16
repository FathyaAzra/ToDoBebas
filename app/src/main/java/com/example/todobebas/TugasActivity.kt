package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import com.example.todobebas.database.TodoAdapter
import com.example.todobebas.databinding.ActivityTugasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TugasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTugasBinding
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate the binding and set the content view
        binding = ActivityTugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.taskRV.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with the onItemClick callback
        adapter = TodoAdapter(
            onItemClick = { updatedTodo ->
                updateTodoInDatabase(updatedTodo) // Handle click to toggle status
            },
            onItemLongClick = { todo ->
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("todoId", todo.todo_id) // Pass the ID of the clicked task
                intent.putExtra("todoName", todo.todo_name) // Optional: Pass more details
                intent.putExtra("todoDate", todo.todo_date)
                this.startActivity(intent)
            }
        )


        binding.taskRV.adapter = adapter

        // Load tasks into the RecyclerView
        loadTodos()

        // Handle the FAB button click
        binding.btnTambah.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        // Setup navigation buttons
        binding.navKalender.setOnClickListener {
            val intent = Intent(this, KalenderActivity::class.java)
            startActivity(intent)
        }
        binding.navStatistik.setOnClickListener {
            val intent = Intent(this, StatistikActivity::class.java)
            startActivity(intent)
        }
    }

    // Load tasks from the database
    private fun loadTodos() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            val todos = database.todoDao().getAll()

            withContext(Dispatchers.Main) {
                adapter.setTodos(todos) // Pass tasks to the adapter
            }
        }
    }

    // Method to update the task status in the database
    private fun updateTodoInDatabase(updatedTodo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().update(updatedTodo)  // Update task status in the database
            loadTodos()  // Reload tasks to reflect changes
        }
    }
}
