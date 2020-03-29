package com.example.temperatureappr.data.model

import com.google.gson.annotations.SerializedName

data class LocationWithWeather(
    @SerializedName("main")
    var main: MainInfo,
    @SerializedName("name")
    var locationName: String,
    @SerializedName("id")
    var locationId: Long
)

data class MainInfo(
    @SerializedName("temp")
    var temp: Double
)