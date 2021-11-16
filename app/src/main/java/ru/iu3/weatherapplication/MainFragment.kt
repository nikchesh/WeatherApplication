package ru.iu3.weatherapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import ru.iu3.weatherapplication.databinding.FragmentMainBinding
import ru.iu3.weatherapplication.presentation.CurrentWeatherViewModel
import ru.iu3.weatherapplication.presentation.OneCallViewModel
import java.util.*
import kotlin.math.round


class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModelCurrentWeather: CurrentWeatherViewModel by viewModels()
    private val viewModelOneCall: OneCallViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private lateinit var client: FusedLocationProviderClient
    private val currentLocale = Locale.getDefault().language
    private var locationLat: Double = 0.0
    private var locationLon: Double = 0.0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager2.adapter = ViewPagerAdapter(viewModelOneCall.oneCallWeather)
        val city = arguments?.getString("City")

        binding.indicator.attachToPager(binding.viewPager2)


        LocationServices.getFusedLocationProviderClient(getActivity()?.getApplicationContext())

        client = LocationServices.getFusedLocationProviderClient(activity)

        if (city == "") {
            checkLocationPermissions()
        }


        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }




        viewModelCurrentWeather.weather.observe(
            viewLifecycleOwner,
            Observer
            {
                if (it != null) {
                    binding.resultTemp.setText((round(it.main.temp)).toInt().toString() + "°C")
                    binding.resultFeelsLike.setText(it.weather[it.weather.size - 1].description)
                    binding.selectedCity.setText(it.name)
                    val lat = it.coord.lat
                    val lon = it.coord.lon
                    loadPicture(it.weather[0].icon)
                    viewModelOneCall.searchOneCall(
                        lat = lat.toString(),
                        lon = lon.toString(),
                        appid = "e75051df5371dc6187f075e88d987509",
                        exclude = "current,minutely,alerts",
                        units = "metric",
                        lang = currentLocale
                    )
                } else {
                    if (city != "") {
                        Toast.makeText(
                            activity,
                            R.string.main_fragmet_error_locartion,
                            Toast.LENGTH_SHORT
                        ).show()
                        checkLocationPermissions()
                    }
                }
            })
        viewModelCurrentWeather.search(
            query = city.toString().trim(' '),
            appid = "e75051df5371dc6187f075e88d987509",
            units = "metric",
            lang = currentLocale
        )

    }

    private fun checkLocationPermissions() {
        if (getActivity()?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED &&
            activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {

            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ), 100
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.size > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation()
        } else
            Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager: LocationManager
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            client.lastLocation.addOnCompleteListener(OnCompleteListener {
                val location: Location
                location = it.result
                if (location != null) {
                    locationLat = location.latitude
                    locationLon = location.longitude
                    searchWithLocationInf(location.latitude, location.longitude)
                } else {
                    val locationRequest: LocationRequest =
                        LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10000)
                            .setFastestInterval(1000)
                            .setNumUpdates(1)
                    val locationCallback: LocationCallback
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)
                            val location1: Location
                            location1 = locationResult.lastLocation
                            locationLat = location1.latitude
                            locationLon = location1.longitude
                            searchWithLocationInf(location1.latitude, location.longitude)
                        }
                    }
                    client.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.myLooper()
                    )
                }
            })
        } else {
            startActivity(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun loadPicture(icon: String) {
        val urlPicture = "https://openweathermap.org/img/wn/" + icon + "@2x.png"
        Glide
            .with(this)
            .load(urlPicture)
            .into(binding.ImageWeatherView);
    }


    private fun searchWithLocationInf(lat: Double, lon: Double) {
        viewModelOneCall.searchOneCall(
            lat = lat.toString(),
            lon = lon.toString(),
            appid = "e75051df5371dc6187f075e88d987509",
            exclude = "current,minutely,alerts",
            units = "metric",
            lang = currentLocale
        )
        viewModelCurrentWeather.searchByLocation( //узнаем наименование местности
            lat = lat.toString(),
            lon = lon.toString(),
            appid = "e75051df5371dc6187f075e88d987509",
            units = "metric",
            lang = currentLocale
        )
    }
}





