package ru.iu3.weatherapplication.networking.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OneCallWeatherInformation<day, hour>(
    val daily: List<day>,
    val hourly: List<hour>,
    val timezone_offset: Int
)