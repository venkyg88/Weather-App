package com.example.weatherapp.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherapp.screens.main.MainViewModel

@Composable
fun WeatherMainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    Text(text = "Main screen")
}