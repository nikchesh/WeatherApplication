package ru.iu3.weatherapplication.networking.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllCurrentWeatherInformation<weatherInformation, mainInformation>(
    val weather: List<weatherInformation>,
    val main: mainInformation,
    val name: String,
    val coord: CoordInformation
)

@JsonClass(generateAdapter = true)
data class CoordInformation(
    val lat: Double,
    val lon: Double
)

