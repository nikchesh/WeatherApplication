package ru.iu3.weatherapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.iu3.weatherapplication.networking.data.OneCallRepository
import ru.iu3.weatherapplication.networking.data.models.DaysInformation
import ru.iu3.weatherapplication.networking.data.models.HoursInformation
import ru.iu3.weatherapplication.networking.data.models.OneCallWeatherInformation

class OneCallViewModel: ViewModel() {
    private val repository = OneCallRepository()

    private val weatherLiveData = MutableLiveData<OneCallWeatherInformation<DaysInformation, HoursInformation>?>()
    private var currentSearchJob: Job?= null

    val oneCallWeather: LiveData<OneCallWeatherInformation<DaysInformation, HoursInformation>?>
        get() = weatherLiveData

    fun searchOneCall(lat: String, lon: String, appid: String, exclude: String, units: String, lang: String){
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            kotlin.runCatching {
                repository.searchWeather(lat=lat, lon=lon, appid=appid, exclude=exclude, units=units, lang=lang)
            }.onSuccess {
                weatherLiveData.postValue(it)
            }.onFailure {
                weatherLiveData.postValue(null)
            }
        }
    }
}