package com.example.weatherapp.di


import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.model.Weather
import com.example.weatherapp.utils.BASE_URL
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import strikt.api.expectThat
import strikt.assertions.isA

class AppModuleTest {

    @Before
    fun setUp() {
    }

    @Test
    fun testRetrofitInstance() {
        //Get an instance of Retrofit
        val instance: Retrofit = AppModule().provideRetrofit()
        //Assert that, Retrofit's base url matches to our BASE_URL
        assert(instance.baseUrl().toUrl().toString() == BASE_URL)
    }

    @Test
    fun testRetrofitService() {
        val service = AppModule().provideOpenWeatherApi()
        assert(service is WeatherApi)
    }

    @Test
    fun `check the returned object type`() {
        val weatherApi = AppModule().provideOpenWeatherApi()
        val givenSearchQuery = "Cary"
        val weather = runBlocking { weatherApi.getWeatherByCity(givenSearchQuery) }
        expectThat(weather) {
            isA<Weather>()
        }

        /*expectThat(weather.request) {
            assertThat("is GET method") {
                it.method() == "GET"
            }
            assertThat("has given search query") {
                it.url().queryParameterValues("search") == listOf(givenSearchQuery)
            }
        }*/
    }
}