package ru.iu3.weatherapplication.networking.data

import retrofit2.http.*
import ru.iu3.weatherapplication.networking.data.models.*

interface WeatherApi {

  @GET("/data/2.5/weather?")
    suspend fun searchCurrentWeather(
        @Query ("q") query: String,
        @Query ("appid") appid: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): AllCurrentWeatherInformation<WeatherCurrentInformation, MainCurrentInformation>

    @GET("/data/2.5/weather?")
    suspend fun searchCurrentWeatherByLocation(
        @Query ("lat") lat: String,
        @Query ("lon") lon: String,
        @Query ("appid") appid: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): AllCurrentWeatherInformation<WeatherCurrentInformation, MainCurrentInformation>

    @GET("/data/2.5/onecall?")
    suspend fun searchOneCallInformation(
        @Query ("lat") lat: String,
        @Query ("lon") lon: String,
        @Query ("appid") appid: String,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): OneCallWeatherInformation<DaysInformation, HoursInformation>
}