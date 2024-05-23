package com.exam.personalbill.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exam.personalbill.Database
import com.exam.personalbill.PieChartDraw
import com.exam.personalbill.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class DashBoard : Fragment() {
    private lateinit var pieChart:PieChart
//    private lateinit var pieChartDraw: PieChartDraw
    private lateinit var database: Database
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)
        pieChart = view.findViewById<PieChart>(R.id.piechart)
        database = Database(requireContext())
        setupPieChart()
        loadPieChartData()

        return view
    }
    private fun setupPieChart(){
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Deposits"
        pieChart.setCenterTextSize(24f)
        pieChart.description.isEnabled = false

        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadPieChartData(){
        val deposits = database.getAllDeposits()
        val entires = ArrayList<PieEntry>()

        deposits.forEach{ deposit ->
            entires.add(PieEntry(deposit.amount.toFloat(),deposit.category))
        }
        val dataset = PieDataSet(entires,"Deposit Catagories")
        dataset.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val data = PieData(dataset)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
        pieChart.invalidate()
    }


}