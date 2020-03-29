package com.example.temperatureappr.data.repository

import com.example.temperatureappr.data.model.Temperature
import com.example.temperatureappr.data.networking.TemperatureApi
import com.example.temperatureappr.data.UseCaseResult

interface InsideTemperatureRepository {
    // Suspend is used to await the result from Deferred
    suspend fun getTemperature(): UseCaseResult<Float>
}

class InsideTemperatureRepositoryImpl(private val temperatureApi: TemperatureApi) :
    InsideTemperatureRepository {
    override suspend fun getTemperature(): UseCaseResult<Float> {
        return try {
            val result = temperatureApi.getTemperatureAsync().await()
            UseCaseResult.Success(result.temperature)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}