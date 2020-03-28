package com.example.temperatureappr.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.Utils


class MyChart constructor(
    private var mChart: LineChart,
    private var windowBackground: Int,
    private var colorPrimary: Int,
    private var fadeGreen: Drawable?
) {

    private var yVals: MutableList<Entry> = ArrayList()
    private var set1: LineDataSet? = null
    private var index = 0f

    init {
        setUpDataSetInfo()
    }

    private fun setUpDataSetInfo() {
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f)
        mChart.setBackgroundColor(windowBackground)
        mChart.setDrawGridBackground(false)
        mChart.axisRight.isEnabled = true
        mChart.legend.isEnabled = false
        mChart.setPinchZoom(false)
        mChart.isDoubleTapToZoomEnabled = false
        mChart.description.isEnabled = false
        val x = mChart.xAxis
        x.isEnabled = false
        val y = mChart.axisLeft
        y.setLabelCount(7, true)
        y.textColor = Color.rgb(255, 255, 240)
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE
        y.setDrawAxisLine(false)
        y.yOffset = 5f
        y.xOffset = 10f
        y.axisMaximum = 60f
        y.axisMinimum = 0f
        yVals.add(Entry(1f, 0f))
        set1 = LineDataSet(yVals, "DataSet 1")
        set1!!.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        set1!!.cubicIntensity = 0.1f
        set1!!.setDrawCircles(false)
        set1!!.lineWidth = 2f
        set1!!.circleRadius = 4f
        set1!!.color = colorPrimary
        set1!!.isHighlightEnabled = false
        set1!!.fillAlpha = 65
        set1!!.setDrawFilled(true)
        if (Utils.getSDKInt() >= 18) {
            val drawable: Drawable? = fadeGreen
            set1!!.fillDrawable = drawable
        } else {
            set1!!.fillColor = colorPrimary
        }
        val data = LineData(set1)
        data.setDrawValues(false)
        mChart.data = data
    }

    fun addData(value: Float) {
        if (yVals.size > 20) yVals.removeAt(0)
        if (index > 2000) {
            index = 1f
            yVals.clear()
        }
        yVals.add(
            Entry(
                index++,
                value
            )
        )
        set1!!.values = yVals
        mChart.data.notifyDataChanged()
        mChart.notifyDataSetChanged()
        mChart.animateX(0)
    }

}