package ru.iu3.weatherapplication.networking.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HoursInformation(
    val dt: Int,
    val temp: Double,
    val weather: List<HoursWeatherInformation>
)

@JsonClass(generateAdapter = true)
data class HoursWeatherInformation(
    val icon: String
)
