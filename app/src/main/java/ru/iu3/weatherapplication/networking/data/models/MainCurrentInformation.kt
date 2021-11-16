package ru.iu3.weatherapplication.networking.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainCurrentInformation(
    val temp: Double,
    val feels_like: Double,
)