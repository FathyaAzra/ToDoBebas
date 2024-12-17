package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import com.example.todobebas.database.TodoAdapter
import com.example.todobebas.databinding.ActivityKalenderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class KalenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKalenderBinding
    private lateinit var taskListAdapter: TodoAdapter

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Keep track of currently selected date to reload tasks when necessary
    private var selectedDate: String = dateFormatter.format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityKalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView for tasks
        binding.taskRV.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with the click handlers
        taskListAdapter = TodoAdapter(
            onItemClick = { updatedTodo ->
                updateTodoInDatabase(updatedTodo)
            },
            onItemLongClick = { todo ->
                navigateToEditActivity(todo)
            }
        )
        binding.taskRV.adapter = taskListAdapter

        // Set up CalendarView listener
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = formatDate(year, month, dayOfMonth)
            binding.tasksTextView.text = "Tasks for $selectedDate"
            loadTasksForDate(selectedDate)
        }

        // Set default date
        binding.tasksTextView.text = "Tasks for $selectedDate"
        loadTasksForDate(selectedDate)

        // Handle navigation buttons
        setupNavigation()
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return dateFormatter.format(calendar.time)
    }

    private fun loadTasksForDate(date: String) {
        val calendar = Calendar.getInstance()
        calendar.time = dateFormatter.parse(date) ?: Date() // Safely parse the date

        // Get start and end of the day
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val endOfDay = calendar.timeInMillis

        // Fetch tasks
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            val tasks = database.todoDao().getTasksForDate(startOfDay, endOfDay)

            withContext(Dispatchers.Main) {
                taskListAdapter.setTodos(tasks)
            }
        }
    }

    private fun updateTodoInDatabase(updatedTodo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().update(updatedTodo)

            // Reload tasks for the currently selected date
            loadTasksForDate(selectedDate)
        }
    }

    private fun navigateToEditActivity(todo: Todo) {
        val intent = Intent(this, EditActivity::class.java).apply {
            putExtra("todoId", todo.todo_id)
            putExtra("todoName", todo.todo_name)
            putExtra("todoDate", todo.todo_date)
        }
        startActivity(intent)
    }

    private fun setupNavigation() {
        binding.navTugas.setOnClickListener {
            startActivity(Intent(this, TugasActivity::class.java))
        }

        binding.navKalender.setOnClickListener {
            // Do nothing since we are already in KalenderActivity
        }

        binding.navStatistik.setOnClickListener {
            startActivity(Intent(this, StatistikActivity::class.java))
        }
    }
}
