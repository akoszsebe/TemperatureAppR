package com.example.temperatureappr.data.networking

import com.example.temperatureappr.data.model.Temperature
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface TemperatureApi {
    @GET("api/actualtemperature")
    fun getTemperatureAsync()
            : Deferred<Temperature>
}