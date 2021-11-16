package ru.iu3.weatherapplication.networking.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DaysInformation(
    val dt: Int,
    val temp: TempInformation,
    val weather: List<WeatherDaysInformation>
)

@JsonClass(generateAdapter = true)
data class TempInformation(
    val day: Double,
    val min: Double,
    val max: Double,
)

@JsonClass(generateAdapter = true)
data class WeatherDaysInformation(
    val icon: String
)