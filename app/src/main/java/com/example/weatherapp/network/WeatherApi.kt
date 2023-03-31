package com.example.weatherapp.network

import com.example.weatherapp.model.Weather
import com.example.weatherapp.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET(value = "data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = API_KEY
    ): Weather

    @GET(value = "data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = API_KEY
    ): Weather
}