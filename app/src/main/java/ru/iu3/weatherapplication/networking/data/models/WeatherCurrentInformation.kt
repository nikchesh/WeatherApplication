package ru.iu3.weatherapplication.networking.data.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class WeatherCurrentInformation(
    val description: String,
    val icon: String
)