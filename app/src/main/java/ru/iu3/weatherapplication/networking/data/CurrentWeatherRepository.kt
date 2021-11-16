package ru.iu3.weatherapplication.networking.data

import ru.iu3.weatherapplication.networking.data.models.*


class CurrentWeatherRepository {
    suspend fun searchWeather(
        query: String,
        appid: String,
        units: String,
        lang: String
    ): AllCurrentWeatherInformation<WeatherCurrentInformation, MainCurrentInformation> {
        return Networking.weatherApi.searchCurrentWeather(query, appid, units, lang)
    }

    suspend fun searchWeatherByLocation(
        lat: String,
        lon: String,
        appid: String,
        units: String,
        lang: String
    ): AllCurrentWeatherInformation<WeatherCurrentInformation, MainCurrentInformation> {
        return Networking.weatherApi.searchCurrentWeatherByLocation(lat, lon, appid, units, lang)
    }
}

