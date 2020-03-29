package com.example.temperatureappr.data.model

import com.google.gson.annotations.SerializedName

data class LocationWithWeather(
    @SerializedName("main")
    var main: MainInfo
)

data class MainInfo(
    @SerializedName("temp")
    var temp: Double
)