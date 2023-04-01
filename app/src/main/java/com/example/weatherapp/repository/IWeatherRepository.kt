package com.example.weatherapp.repository

import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather

interface IWeatherRepository {
    suspend fun getWeather(city: String?): DataOrException<Weather, Boolean, Exception>

    suspend fun getWeatherByCoordinates(lat: String, lon: String): DataOrException<Weather, Boolean, Exception>
}