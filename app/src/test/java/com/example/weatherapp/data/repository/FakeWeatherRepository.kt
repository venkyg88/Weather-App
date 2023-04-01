package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.model.*
import com.example.weatherapp.utils.DataOrException

class FakeWeatherRepository : IWeatherRepository {

    val weather = Weather(
        "Stations",
        Clouds(40),
        200,
        Coord(37.222, -122.2343),
        1680294547,
        5375480,
        Main(287.39, 54, 1023, 288.4, 291.2, 285.13),
        "Mountain View",
        Sys("US", 2010364, 1680270923, 1680316161, 2),
        timezone = -25200,
        visibility = 10000,
        weather = listOf(WeatherX("scattered clouds", "03d", 802, "Clouds")),
        Wind(360, 4.12, 34.0)
    )


    override suspend fun getWeather(city: String?): DataOrException<Weather, Boolean, Exception> {
        if (city != weather.name) {
            return DataOrException(e = IllegalArgumentException("Invalid city value entered")
            )
        }
        return DataOrException(data = weather)
    }

    override suspend fun getWeatherByCoordinates(
        lat: String,
        lon: String,
    ): DataOrException<Weather, Boolean, Exception> {
        return DataOrException(data = weather)
    }
}