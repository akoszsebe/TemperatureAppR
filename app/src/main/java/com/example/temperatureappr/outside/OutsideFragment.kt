package com.example.temperatureappr.outside

import android.os.Bundle
import android.view.View
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentOutsideBinding
import com.example.temperatureappr.utils.MyChart

private const val ANIMATION_DURATION = 2500
private const val PROGRESS_SHIFT = 1.66f

class OutsideFragment :
    BaseFragment<FragmentOutsideBinding, OutsideViewModel>(R.layout.fragment_outside) {
    private lateinit var myChart: MyChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = OutsideViewModel()
        myChart = MyChart(
            binding.myChart,
            requireContext().resources.getColor(R.color.windowBackground),
            requireContext().resources.getColor(R.color.colorAccent),
            requireContext().getDrawable(R.drawable.fade_pink)
        )
        subscribeUi()
    }

    private fun subscribeUi() {
        displayCircularProgressBarChange(0f)
        displayTemperatureTextChange(0f)

        // todo request in view model
        val temperature = 14f
        displayCircularProgressBarChange(temperature)
        displayTemperatureTextChange(temperature)
        displayChartChange(temperature)
        displayChartChange(temperature)
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