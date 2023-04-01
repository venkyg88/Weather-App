package com.example.weatherapp.ui.screens.main

import com.example.weatherapp.data.repository.FakeWeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    var weatherRepository = FakeWeatherRepository()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(weatherRepository)
    }

    @Test
    fun check_weather_country() = runBlocking {
        val country = viewModel.getWeatherByCity("Mountain View").data?.sys?.country
        assertTrue("US" == country)
    }

    @Test
    fun check_weather_coordinates() = runBlocking {
        val city = viewModel.getWeatherByCoordinates("37.222", "-122.2343").data?.name
        assertTrue("Mountain View" == city)
    }

    @Test
    fun check_for_wrong_city_passed() = runBlocking {
        val response = viewModel.getWeatherByCity("1234")
        assertTrue(response.e?.message == "Invalid city value entered")
    }

    
}