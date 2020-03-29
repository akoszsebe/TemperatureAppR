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
        mChart.apply {
            setViewPortOffsets(0f, 0f, 0f, 0f)
            setBackgroundColor(windowBackground)
            setDrawGridBackground(false)
            axisRight.isEnabled = true
            legend.isEnabled = false
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            description.isEnabled = false
        }
        mChart.xAxis.apply {
            isEnabled = false
        }
        mChart.axisLeft.apply {
            setLabelCount(7, true)
            textColor = Color.rgb(255, 255, 240)
            setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            setDrawGridLines(false)
            axisLineColor = Color.WHITE
            setDrawAxisLine(false)
            yOffset = 5f
            xOffset = 10f
            axisMaximum = 60f
            axisMinimum = 0f
        }
        yVals.add(Entry(1f, 0f))
        set1 = LineDataSet(yVals, "DataSet 1").apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            cubicIntensity = 0.1f
            setDrawCircles(false)
            lineWidth = 2f
            circleRadius = 4f
            color = colorPrimary
            isHighlightEnabled = false
            fillAlpha = 65
            setDrawFilled(true)
        }
        if (Utils.getSDKInt() >= 18) {
            val drawable: Drawable? = fadeGreen
            set1?.apply { fillDrawable = drawable }
        } else {
            set1?.apply { fillColor = colorPrimary }
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
        set1.let {
            if (it != null) {
                it.values = yVals
            }
        }
        mChart.data.notifyDataChanged()
        mChart.notifyDataSetChanged()
        mChart.animateX(0)
    }

}