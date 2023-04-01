package com.example.weatherapp.ui.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherapp.model.Weather
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.formatDateTime
import com.example.weatherapp.utils.formatDecimals
import com.example.weatherapp.R

@Composable
fun WeatherStateImage(imageUrl: String) {
    /*
        Here, we make use of coil. An image loading library for Android backed by Kotlin Coroutines. Coil is fast,
        It performs a number of optimizations including memory and disk caching, downsampling the
        image in memory, automatically pausing/cancelling requests, and more.
     */
    Image(painter = rememberImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = Modifier.size(180.dp))
}

@Composable
fun SunsetSunRiseRow(weather: Weather) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row {
            Image(painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier.size(50.dp))
            Text(text = formatDateTime(weather.sys.sunrise),
                style = MaterialTheme.typography.caption)

        }

        Row {

            Text(text = formatDateTime(weather.sys.sunset),
                style = MaterialTheme.typography.caption)
            Image(painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: Weather,
                            isImperial: Boolean) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(40.dp))
            Text(text = "${weather.main.humidity}%",
                style = MaterialTheme.typography.caption)

        }

        Row() {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(40.dp))
            Text(text = "${weather.main.pressure} psi",
                style = MaterialTheme.typography.caption)

        }

        Row() {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(40.dp))
            Text(text = "${formatDecimals(weather.wind.speed)} " + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption)

        }
    }
}

