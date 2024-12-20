package com.example.todobebas

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import com.example.todobebas.database.TodoAdapter
import com.example.todobebas.databinding.ActivityTugasBinding
//import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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
        adapter = TodoAdapter(
            onItemClick = { updatedTodo ->
                updateTodoInDatabase(updatedTodo) // Toggle status
            },
            onItemLongClick = { todo ->
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("todoId", todo.todo_id)
                intent.putExtra("todoName", todo.todo_name)
                intent.putExtra("todoDate", todo.todo_date)
                this.startActivity(intent)
            }
        )
        binding.taskRV.adapter = adapter

        // Tambahkan ItemTouchHelper untuk Swipe Delete
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // Tidak digunakan
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val todoToDelete = adapter.getTodoAt(position)

                // Hapus data dari database
                deleteTodoFromDatabase(todoToDelete)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
//                RecyclerViewSwipeDecorator.Builder(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@TugasActivity, R.color.red))
//                    .addSwipeLeftActionIcon(R.drawable.delete_24px) // Tambahkan icon delete di drawable
//                    .create()
//                    .decorate()
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.taskRV)

        // Load tasks into the RecyclerView
        loadTodos()

        // Handle the FAB button click
        binding.btnTambah.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        // Setup navigation buttons
        binding.navKalender.setOnClickListener {
            startActivity(Intent(this, KalenderActivity::class.java))
        }
        binding.navStatistik.setOnClickListener {
            startActivity(Intent(this, StatistikActivity::class.java))
        }
    }

    // Load tasks from the database
    private fun loadTodos() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            val todos = database.todoDao().getAll()

            withContext(Dispatchers.Main) {
                adapter.setTodos(todos)
            }
        }
    }

    // Delete task from the database
    private fun deleteTodoFromDatabase(todo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().delete(todo) // Hapus dari database

            withContext(Dispatchers.Main) {
                loadTodos() // Reload RecyclerView
            }
        }
    }

    // Update task in the database
    private fun updateTodoInDatabase(updatedTodo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().update(updatedTodo)
            loadTodos()
        }
    }
}
