package com.example.temperatureappr.data.repository

import com.example.temperatureappr.data.UseCaseResult
import com.example.temperatureappr.data.model.LocationWithWeather
import com.example.temperatureappr.data.networking.WeatherApi

interface OutsideTemperatureRepository {
    suspend fun getTemperature(currentLocation: Long): UseCaseResult<LocationWithWeather>
    suspend fun getTemperatureLatLon(lat: Double, lon: Double): UseCaseResult<LocationWithWeather>
}

class OutsideTemperatureRepositoryImpl(private val weatherApi: WeatherApi) :
    OutsideTemperatureRepository {
    override suspend fun getTemperature(currentLocation: Long): UseCaseResult<LocationWithWeather> {
        return try {
            val result = weatherApi.getWeatherForLocationWithId(currentLocation).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getTemperatureLatLon(lat: Double, lon: Double): UseCaseResult<LocationWithWeather> {
        return try {
            val result = weatherApi.getWeatherForLocationWithLatLon(lat, lon).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}