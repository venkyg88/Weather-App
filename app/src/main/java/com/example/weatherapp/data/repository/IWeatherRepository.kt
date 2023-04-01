package com.example.weatherapp.data.repository

import com.example.weatherapp.utils.DataOrException
import com.example.weatherapp.model.Weather

interface IWeatherRepository {
    suspend fun getWeather(city: String?): DataOrException<Weather, Boolean, Exception>

    suspend fun getWeatherByCoordinates(lat: String, lon: String): DataOrException<Weather, Boolean, Exception>
}