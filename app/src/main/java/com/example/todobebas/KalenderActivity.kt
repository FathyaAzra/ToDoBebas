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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityKalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView for tasks
        binding.taskRV.layoutManager = LinearLayoutManager(this)


        // Initialize the adapter with the click handler for updating task status
        taskListAdapter = TodoAdapter(
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

        binding.taskRV.adapter = taskListAdapter

        // Set up the CalendarView listener to update tasks based on selected date
        val calendarView = binding.calendarView
        val tasksTextView = binding.tasksTextView

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = formatDate(year, month, dayOfMonth)
            tasksTextView.text = "Tasks for $selectedDate"
            loadTasksForDate(selectedDate)
        }

        // Handle navigation buttons (Tugas, Kalender, Statistik)
        binding.navTugas.setOnClickListener {
            val intent = Intent(this, TugasActivity::class.java)
            startActivity(intent)
        }

        binding.navKalender.setOnClickListener {
            // Already in KalenderActivity, no need to do anything
        }

        binding.navStatistik.setOnClickListener {
            val intent = Intent(this, StatistikActivity::class.java)
            startActivity(intent)
        }
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return dateFormatter.format(calendar.time)
    }

    private fun loadTasksForDate(selectedDate: String) {
        val calendar = Calendar.getInstance()
        calendar.time = dateFormatter.parse(selectedDate) ?: Date()

        // Get the start of the day (00:00:00)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        // Get the end of the day (23:59:59)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val endOfDay = calendar.timeInMillis

        // Fetch tasks for the date range (start to end of the day)
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            val tasks = database.todoDao().getTasksForDate(startOfDay, endOfDay)

            withContext(Dispatchers.Main) {
                taskListAdapter.setTodos(tasks)  // Update the adapter with tasks for the selected date
            }
        }
    }

    // Method to update the task status in the database
    private fun updateTodoInDatabase(updatedTodo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().update(updatedTodo)  // Update task status in the database
            loadTasksForDate(dateFormatter.format(Date()))  // Reload tasks for the current date
        }
    }
}
