package com.example.temperatureappr.inside

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentInsideBinding
import com.example.temperatureappr.utils.MyChart
import com.example.temperatureappr.utils.Constants.Companion.ANIMATION_DURATION
import com.example.temperatureappr.utils.Constants.Companion.PROGRESS_SHIFT

class InsideFragment :
    BaseFragment<FragmentInsideBinding, InsideViewModel>(
        R.layout.fragment_inside,
        InsideViewModel::class
    ) {
    private lateinit var myChart: MyChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myChart = MyChart(
            binding.myChart,
            requireContext().resources.getColor(R.color.windowBackground),
            requireContext().resources.getColor(R.color.colorGreen),
            requireContext().getDrawable(R.drawable.fade_green)
        )
        initViewModel()
    }

    override fun initViewModel() {
        displayCircularProgressBarChange(0f)
        displayTemperatureTextChange(0f)
        displayChartChange(0f)
        viewModel.temperature.observe(viewLifecycleOwner, Observer { newTemperature ->
            displayCircularProgressBarChange(newTemperature)
            displayTemperatureTextChange(newTemperature)
            displayChartChange(newTemperature)
        })
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

    private fun displayChartChange(temperature: Float) {
        myChart.addData(temperature)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startLoadTemperatureRepeated()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLoadTemperatureRepeated()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopLoadTemperatureRepeated()
    }
}