package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.utils.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.data.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) : IWeatherRepository {

    /*
        Suspendable functions which pass city and return weather data on success and exception on failure
     */
    override suspend fun getWeather(city: String?): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeatherByCity(city = city)

        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    /*
        Suspendable functions which pass location and return weather data on success and exception on failure
     */
    override suspend fun getWeatherByCoordinates(
        latitude: String,
        longitude: String,
    ): DataOrException<Weather, Boolean, Exception> {

        val response = try {
            api.getWeatherByCoordinates(latitude = latitude, longitude = longitude)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

}