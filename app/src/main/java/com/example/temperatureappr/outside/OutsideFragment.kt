package com.example.temperatureappr.outside

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentOutsideBinding
import com.example.temperatureappr.homeviewpager.HomeViewPagerFragmentDirections
import com.example.temperatureappr.utils.MyChart

private const val ANIMATION_DURATION = 2500
private const val PROGRESS_SHIFT = 1.66f

class OutsideFragment :
    BaseFragment<FragmentOutsideBinding, OutsideViewModel>(
        R.layout.fragment_outside,
        OutsideViewModel::class
    ) {
    private lateinit var myChart: MyChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myChart = MyChart(
            binding.myChart,
            requireContext().resources.getColor(R.color.windowBackground),
            requireContext().resources.getColor(R.color.colorPink),
            requireContext().getDrawable(R.drawable.fade_pink)
        )

        binding.buttonGps.setOnClickListener {
            val direction =
                HomeViewPagerFragmentDirections.actionOutsideFragmentToLocationSetupFragment()
            view.findNavController().navigate(direction)
        }

        initViewModel()
    }

    override fun initViewModel() {
        displayCircularProgressBarChange(0f)
        displayTemperatureTextChange(0f)
        displayChartChange(0f)
        viewModel.temperatureData.observe(viewLifecycleOwner, Observer { newTemperature ->
            val temperature = newTemperature.main.temp.toFloat()
            binding.textViewLocation.text = newTemperature.locationName
            displayCircularProgressBarChange(temperature)
            displayTemperatureTextChange(temperature)
            displayChartChange(temperature)
        })

        val currentLocation = sharedPrefs.getCurrentLocation()
        viewModel.loadTemperature(currentLocation)
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
}