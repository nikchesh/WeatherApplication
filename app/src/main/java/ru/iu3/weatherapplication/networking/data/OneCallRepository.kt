package ru.iu3.weatherapplication.networking.data


import ru.iu3.weatherapplication.networking.data.models.*

class OneCallRepository {
    suspend fun searchWeather(
        lat: String,
        lon: String,
        appid: String,
        exclude: String,
        units: String,
        lang: String
    ): OneCallWeatherInformation<DaysInformation, HoursInformation> {
        return Networking.weatherApi.searchOneCallInformation(lat, lon, appid, exclude, units, lang)
    }
}

