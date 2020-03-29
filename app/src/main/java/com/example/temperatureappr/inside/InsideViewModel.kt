package com.example.temperatureappr.inside

import androidx.lifecycle.MutableLiveData
import com.example.temperatureappr.base.BaseViewModel
import com.example.temperatureappr.data.SingleLiveEvent
import com.example.temperatureappr.data.model.Temperature
import com.example.temperatureappr.data.repository.InsideTemperatureRepository
import com.example.temperatureappr.data.UseCaseResult
import kotlinx.coroutines.*

class InsideViewModel(private val insideTemperatureRepository: InsideTemperatureRepository) : BaseViewModel() {

    val temperature = MutableLiveData<Float>()

    fun loadTemperature() {
        launch {
            when (val result =
                withContext(Dispatchers.IO) { insideTemperatureRepository.getTemperature() }) {
                is UseCaseResult.Success -> temperature.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }
}