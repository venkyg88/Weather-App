package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(city: String?)
    : DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeatherByCity(city = city)

        }catch (e: Exception){
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return  DataOrException(data = response)
    }

    suspend fun getWeatherByCoordinates(
        latitude: String,
        longitude: String,
    ): DataOrException<Weather, Boolean, Exception> {

        Log.d("coordinates","${latitude} +, ${longitude}")
        val response = try {
            api.getWeatherByCoordinates(latitude = latitude, longitude = longitude)
        }catch (e: Exception){
            Log.d("Repo response by coordinates", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return  DataOrException(data = response)
    }

}