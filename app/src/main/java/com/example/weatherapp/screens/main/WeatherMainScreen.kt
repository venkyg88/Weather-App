package com.example.weatherapp.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.LocationCoordinates
import com.example.weatherapp.model.Weather
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.main.MainViewModel
import com.example.weatherapp.uicomponents.HumidityWindPressureRow
import com.example.weatherapp.uicomponents.SunsetSunRiseRow
import com.example.weatherapp.uicomponents.WeatherStateImage
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.formatDecimals
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun WeatherMainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    locationCoordinates: LocationCoordinates?,
    city: String?
) {
    Log.d("TAG", "WeatherMainScreen: ${city}")
    Log.d("TAG", "Coordinates: ${locationCoordinates?.latitude + ", " + locationCoordinates?.longitude}")
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {

        value = if (city?.trim().isNullOrEmpty() && locationCoordinates != null) {
            val (latitude, longitude) = locationCoordinates!!
            mainViewModel.getWeatherByCoordinates(latitude, longitude)
        } else mainViewModel.getWeatherByCity(city = city)
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController = navController)
    } else {
        ErrorMessage()
    }
}

@Composable
fun ErrorMessage() {
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Failed to load the data, could be any of the reasons below:")
        Text(text = "1. GPS unavailable, please check permissions and enable to access full features")
        Text(text = "2. Incorrect search value entered, please enter the suggested format")
    }

}

@Composable
fun MainScaffold(weather: Weather, navController: NavHostController) {

    Scaffold(topBar = {
        WeatherAppBar(title = weather.name + ", " + weather.sys.country,
            navController = navController,
            onAddAction = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp) {
            Log.d("TAG", "MainScaffold: Button clicked")
        }
    }) {
        MainContent(data = weather)

    }
}

@Composable
fun MainContent(data: Weather) {
    val weatherIcon = data.weather[0].icon
    val imageUrl = "https://openweathermap.org/img/wn/${weatherIcon}@2x.png"

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatDate(data.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp))

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(300.dp),
            shape = RectangleShape,
            color = Color(0xFFC4A7CC)) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(data.main.temp) + "º",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold)
                Text(text = data.weather[0].description,
                    fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = data, isImperial = true)
        Divider()
        SunsetSunRiseRow(weather = data)
        /*Button(onClick = { *//*TODO*//* },
            modifier = Modifier.padding(5.dp)
                .fillMaxHeight(5f)
                .fillMaxWidth(10f)) {

        }*/
    }
}
