package com.example.temperatureappr.inside

import android.os.Bundle
import android.view.View
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentInsideBinding
import com.example.temperatureappr.utils.MyChart

private const val ANIMATION_DURATION = 2500
private const val PROGRESS_SHIFT = 1.66f

class InsideFragment :
    BaseFragment<FragmentInsideBinding, InsideViewModel>(R.layout.fragment_inside) {
    private lateinit var myChart: MyChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = InsideViewModel()
        myChart = MyChart(
            binding.myChart,
            requireContext().resources.getColor(R.color.windowBackground),
            requireContext().resources.getColor(R.color.colorPrimary),
            requireContext().getDrawable(R.drawable.fade_green)
        )
        subscribeUi()
    }

    private fun subscribeUi() {
        displayCircularProgressBarChange(0f)
        displayTemperatureTextChange(0f)

        // todo request in view model
        val _temperature = 23f
        displayCircularProgressBarChange(_temperature)
        displayTemperatureTextChange(_temperature)
        displayChartChange(_temperature)
        displayChartChange(_temperature)
    }

    private fun displayCircularProgressBarChange(temperature: Float) {
        binding.circleProgressbar.setProgressWithAnimation(
            temperature * PROGRESS_SHIFT,
            ANIMATION_DURATION
        )
    }

    private fun displayTemperatureTextChange(temperature: Float) {
        binding.temperature.text = String.format("%s", temperature)
    }

    fun displayChartChange(temperature: Float) {
        myChart.addData(temperature)
    }
}