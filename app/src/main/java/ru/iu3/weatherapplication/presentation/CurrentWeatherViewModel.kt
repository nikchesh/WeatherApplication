package ru.iu3.weatherapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.iu3.weatherapplication.networking.data.CurrentWeatherRepository
import ru.iu3.weatherapplication.networking.data.models.AllCurrentWeatherInformation
import ru.iu3.weatherapplication.networking.data.models.MainCurrentInformation
import ru.iu3.weatherapplication.networking.data.models.WeatherCurrentInformation

class CurrentWeatherViewModel : ViewModel() {

    private val repository = CurrentWeatherRepository()

    private val weatherLiveData =
        MutableLiveData<AllCurrentWeatherInformation<WeatherCurrentInformation, MainCurrentInformation>?>()

    private var currentSearchJob: Job? = null

    val weather: LiveData<AllCurrentWeatherInformation<WeatherCurrentInformation, MainCurrentInformation>?>
        get() = weatherLiveData

    fun search(query: String, appid: String, units: String, lang: String) {
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            runCatching {
                repository.searchWeather(query = query, appid = appid, units = units, lang = lang)
            }.onSuccess {
                weatherLiveData.postValue(it)
            }.onFailure {
                weatherLiveData.postValue(null)
            }
        }
    }

    fun searchByLocation(lat: String, lon: String,appid: String, units: String, lang: String) {
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            runCatching {
                repository.searchWeatherByLocation(lat = lat,lon=lon, appid = appid, units = units, lang = lang)
            }.onSuccess {
                weatherLiveData.postValue(it)
            }.onFailure {
                weatherLiveData.postValue(null)
            }
        }
    }

}
