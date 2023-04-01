package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.model.LocationCoordinates
import com.example.weatherapp.screens.WeatherMainScreen
import com.example.weatherapp.screens.WeatherSplashScreen
import com.example.weatherapp.screens.main.MainViewModel
import com.example.weatherapp.screens.search.SearchScreen

@Composable
fun WeatherNavigation(locationCoordinates: LocationCoordinates?) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable("${route}/{city}",
                arguments = listOf(
                    navArgument(name = "city") {
                        type = NavType.StringType
                    })) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                WeatherMainScreen(navController = navController,
                    mainViewModel,
                    locationCoordinates,
                    city = city)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}


