package com.example.todobebas.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todobebas.databinding.FragmentStatistikBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StatistikFragment : Fragment() {

    /*private var _binding: FragmentStatistikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statistikViewModel =
            ViewModelProvider(this).get(StatistikViewModel::class.java)

        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        statistikViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Initialize the PieChart
        val pieChart: PieChart = binding.chart
        setupPieChart(pieChart)

        return root
    }

    private fun setupPieChart(pieChart: PieChart) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(40f, "Category 1"))
        entries.add(PieEntry(60f, "Category 2"))

        val dataSet = PieDataSet(entries, "Statistics")
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate() // Refresh the chart
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
}
