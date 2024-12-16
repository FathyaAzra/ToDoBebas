package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todobebas.database.AppDatabase
import com.example.todobebas.database.Todo
import com.example.todobebas.database.TodoAdapter
import com.example.todobebas.databinding.ActivityStatistikBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatistikActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatistikBinding
    private lateinit var taskListAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityStatistikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView for task list
        binding.taskList.layoutManager = LinearLayoutManager(this)
        taskListAdapter = TodoAdapter { updatedTodo ->
            updateTodoInDatabase(updatedTodo)
        }
        binding.taskList.adapter = taskListAdapter

        // Load statistics and set up the PieChart
        loadTaskStatistics()

        // Handle navigation buttons (Tugas, Kalender, Statistik)
        setupNavigation()
    }

    private fun loadTaskStatistics() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            val todos = database.todoDao().getAll() // Fetch all tasks

            // Calculate counts for each category
            val notYetCount = todos.count { it.todo_status == "not yet" }
            val ongoingCount = todos.count { it.todo_status == "on going" }
            val doneCount = todos.count { it.todo_status == "done" }

            // Update the PieChart with data
            withContext(Dispatchers.Main) {
                setupPieChart(binding.chart, todos, notYetCount, ongoingCount, doneCount)
            }
        }
    }

    private fun setupPieChart(pieChart: PieChart, todos: List<Todo>, notYet: Int, ongoing: Int, done: Int) {
        val entries = ArrayList<PieEntry>().apply {
            if (notYet > 0) add(PieEntry(notYet.toFloat(), "Not Yet"))
            if (ongoing > 0) add(PieEntry(ongoing.toFloat(), "Ongoing"))
            if (done > 0) add(PieEntry(done.toFloat(), "Done"))
        }

        val dataSet = PieDataSet(entries, "Task Status")
        dataSet.setColors(
            getColor(R.color.darkblue),  // "Not Yet"
            getColor(R.color.magenta),  // "Ongoing"
            getColor(R.color.lightblue) // "Done"
        )

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate() // Refresh the chart

        // Set listener for category selection
        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e is PieEntry) { // Cast the Entry to PieEntry
                    val selectedCategory = e.label
                    filterTasksByCategory(todos, selectedCategory)
                }
            }

            override fun onNothingSelected() {
                // Do nothing when no category is selected
            }
        })

    }

    private fun filterTasksByCategory(todos: List<Todo>, category: String) {
        // Filter tasks based on the selected category
        val filteredTasks = when (category) {
            "Not Yet" -> todos.filter { it.todo_status == "not yet" }
            "Ongoing" -> todos.filter { it.todo_status == "on going" }
            "Done" -> todos.filter { it.todo_status == "done" }
            else -> emptyList()
        }

        // Update the RecyclerView with the filtered list
        taskListAdapter.setTodos(filteredTasks)
    }

    // Update the task status in the database
    private fun updateTodoInDatabase(updatedTodo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            database.todoDao().update(updatedTodo)
            loadTaskStatistics() // Reload the chart and tasks
        }
    }

    private fun setupNavigation() {
        binding.navTugas.setOnClickListener {
            val intent = Intent(this, TugasActivity::class.java)
            startActivity(intent)
        }

        binding.navKalender.setOnClickListener {
            val intent = Intent(this, KalenderActivity::class.java)
            startActivity(intent)
        }

        binding.navStatistik.setOnClickListener {
            // Already in StatistikActivity, no need to do anything
        }
    }
}
