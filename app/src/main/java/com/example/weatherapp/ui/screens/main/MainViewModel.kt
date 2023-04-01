package com.example.weatherapp.ui.screens.main

import androidx.lifecycle.ViewModel
import com.example.weatherapp.utils.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.data.repository.IWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: IWeatherRepository) :
    ViewModel() {

    /*
        suspendable function that takes the searched value and calls repository for a response
     */
    suspend fun getWeatherByCity(city: String?): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city = city)
    }

    /*
        suspendable function called when the location coordinates are know which in turn passes
        the them to repository for a response
     */
    suspend fun getWeatherByCoordinates(
        latitude: String,
        longitude: String,
    ): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeatherByCoordinates(latitude, longitude)
    }

}