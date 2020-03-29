package com.example.temperatureappr.outside

import androidx.lifecycle.MutableLiveData
import com.example.temperatureappr.base.BaseViewModel
import com.example.temperatureappr.data.UseCaseResult
import com.example.temperatureappr.data.repository.OutsideTemperatureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OutsideViewModel(private val outsideTemperatureRepository: OutsideTemperatureRepository) :
    BaseViewModel() {

    val temperature = MutableLiveData<Float>()

    fun loadTemperature() {
        launch {
            when (val result =
                withContext(Dispatchers.IO) { outsideTemperatureRepository.getTemperature() }) {
                is UseCaseResult.Success -> temperature.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }
}