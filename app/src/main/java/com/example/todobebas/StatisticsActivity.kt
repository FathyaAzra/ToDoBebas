package com.example.todobebas

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_statistik)

        val pieChart: PieChart = findViewById(R.id.chart)

        // Data dummy untuk PieChart
        val dataEntries = listOf(
            PieEntry(40f, "Completed"),
            PieEntry(30f, "In Progress"),
            PieEntry(20f, "Pending"),
            PieEntry(10f, "Overdue")
        )

        // Membuat DataSet untuk PieChart
        val dataSet = PieDataSet(dataEntries, "Task Status")
        dataSet.colors = listOf(
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.RED
        )
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        // Menyiapkan data untuk PieChart
        val pieData = PieData(dataSet)

        // Konfigurasi PieChart
        pieChart.data = pieData
        pieChart.description.isEnabled = false // Menghilangkan deskripsi
        pieChart.setDrawHoleEnabled(true) // Lubang di tengah
        pieChart.holeRadius = 40f
        pieChart.transparentCircleRadius = 45f
        pieChart.setUsePercentValues(true) // Menampilkan data dalam persen
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)

        // Menyesuaikan legenda
        val legend: Legend = pieChart.legend
        legend.isEnabled = true
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        // Refresh chart
        pieChart.invalidate()
    }
}
