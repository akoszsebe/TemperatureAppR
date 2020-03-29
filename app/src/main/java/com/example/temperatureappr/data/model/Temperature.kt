package com.example.temperatureappr.data.model

import com.google.gson.annotations.SerializedName


data class Temperature(
    @SerializedName("temperature")
    var temperature: Float
)