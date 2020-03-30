package com.example.temperatureappr.outside

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentOutsideBinding
import com.example.temperatureappr.homeviewpager.HomeViewPagerFragmentDirections
import com.example.temperatureappr.utils.MyChart
import com.example.temperatureappr.utils.Constants.Companion.ANIMATION_DURATION
import com.example.temperatureappr.utils.Constants.Companion.PROGRESS_SHIFT

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
            ContextCompat.getColor(requireContext(),R.color.windowBackground),
            ContextCompat.getColor(requireContext(), R.color.colorPink),
            ContextCompat.getDrawable(requireContext(), R.drawable.fade_pink)
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
        val currentLocation = sharedPrefs.getCurrentLocation()
        viewModel.loadTemperature(currentLocation)
    }
}