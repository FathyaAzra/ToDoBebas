package com.example.todobebas

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todobebas.databinding.ActivityStatistikBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StatistikActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatistikBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityStatistikBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Set the root view of the activity using ViewBinding

        // Set up the PieChart
        val pieChart: PieChart = binding.chart
        setupPieChart(pieChart)

        // Set the title and description text
        val textView: TextView = binding.textNotifications
        textView.text = "Statistik Saya" // You can modify the text as needed

        // Handle navigation buttons (Tugas, Kalender, Statistik)
        setupNavigation()
    }

    // Set up the PieChart with data
    private fun setupPieChart(pieChart: PieChart) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(40f, "Category 1"))
        entries.add(PieEntry(60f, "Category 2"))

        val dataSet = PieDataSet(entries, "Statistics")
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate() // Refresh the chart
    }

    // Set up navigation button listeners
    private fun setupNavigation() {
        binding.navTugas.setOnClickListener {
            // Navigate to TugasActivity
            val intent = Intent(this, TugasActivity::class.java)
            startActivity(intent)
        }

        binding.navKalender.setOnClickListener {
            // Navigate to KalenderActivity
            val intent = Intent(this, KalenderActivity::class.java)
            startActivity(intent)
        }

        binding.navStatistik.setOnClickListener {
            // Already in StatistikActivity, no need to do anything
        }
    }
}
