package com.example.weatherapp.ui.screens.main

import android.window.SplashScreen
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.weatherapp.model.LocationCoordinates
import com.example.weatherapp.ui.navigation.WeatherScreens
import com.example.weatherapp.ui.screens.WeatherMainScreen
import com.example.weatherapp.ui.screens.WeatherSplashScreen
import com.example.weatherapp.ui.screens.search.SearchBar
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherMainScreenTest {

    @get:Rule
    val rule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            WeatherMainScreen(navController,
                hiltViewModel(),
                LocationCoordinates("37", "-122"),
                null)
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun myTest() {
        rule.setContent {
            Text("You can set any Compose content!")
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        rule.onNodeWithTag(WeatherScreens.MainScreen.name).performClick()
    }

    @Test
    fun enterLocation_showsWeather() {
        rule.setContent { }
    }
}