package com.example.temperatureappr.data.repository

import com.example.temperatureappr.data.UseCaseResult
import com.example.temperatureappr.data.networking.WeatherApi

interface OutsideTemperatureRepository {
    suspend fun getTemperature(): UseCaseResult<Float>
}

class OutsideTemperatureRepositoryImpl(private val weatherApi: WeatherApi) :
    OutsideTemperatureRepository {
    override suspend fun getTemperature(): UseCaseResult<Float> {
        return try {
            val result = weatherApi.getWeatherForLocationWithLatLon(34.6,46.7).await()
            UseCaseResult.Success(result.main.temp.toFloat())
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}