package com.example.temperatureappr.data.networking

import com.example.temperatureappr.BuildConfig
import com.example.temperatureappr.data.model.LocationWithWeather
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeatherForLocationWithLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.OPENWEATHERMAP_KEY,
        @Query("units") units:String  = "metric"
    ): Deferred<LocationWithWeather>
}