package com.example.weatherapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
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
import com.example.weatherapp.utils.DataOrException
import com.example.weatherapp.model.LocationCoordinates
import com.example.weatherapp.model.Weather
import com.example.weatherapp.ui.navigation.WeatherScreens
import com.example.weatherapp.ui.screens.main.MainViewModel
import com.example.weatherapp.ui.uicomponents.HumidityWindPressureRow
import com.example.weatherapp.ui.uicomponents.SunsetSunRiseRow
import com.example.weatherapp.ui.uicomponents.WeatherStateImage
import com.example.weatherapp.utils.IMAGE_BASE_URL
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.formatDecimals
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun WeatherMainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    locationCoordinates: LocationCoordinates?,
    city: String?,
) {

    /*
    Here are we are using one of the side-effects in Compose called produceState, which gives a coroutine scope.
    The main use of it is it converts non-compose state to compose state
     */
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        // When the coordinates are known and the passed city is empty, we make a network call with latitude and longitude to fetch weather
        // When the coordinates are not know and city is empty, we make a network call using a default city cary
        // When the coordinates are not know and the city is passed via search we make a network call using the city search
        value = if (city?.trim().isNullOrEmpty() && locationCoordinates != null) {
            val (latitude, longitude) = locationCoordinates
            mainViewModel.getWeatherByCoordinates(latitude, longitude)
        } else if (city?.trim().isNullOrEmpty()) {
            mainViewModel.getWeatherByCity("Cary")
        } else {
            mainViewModel.getWeatherByCity(city = city)
        }
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController = navController)
    } else {
        ErrorMessage()
    }
}

/*
    Error handling when the GPS is not made available or invalid search value is entered
 */
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

/*
    On successful weather data retrieval we show the AppBar with current location and option to search
    and the screen contains the view of weather data like the time, image, temperature, sunset, sunrise time,
    wind, pressure, etc,.

 */
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
    // To get the image, we make use of the image url passing the icon code we received from weather object
    val imageUrl = IMAGE_BASE_URL + "${weatherIcon}@2x.png"

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date formatted from Unix time stamp, ex: Sat, Apr 1
        Text(text = formatDate(data.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp))

        // compose elements that make use of re-usable ui components to produce the Mainscreen ui
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
