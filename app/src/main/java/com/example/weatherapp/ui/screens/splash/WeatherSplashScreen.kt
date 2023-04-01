package com.example.weatherapp.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    /*
        LaunchEffect is one of the side-effects of the Composable functions
        To call suspend functions safely from inside a composable, use the LaunchedEffect composable.
        When LaunchedEffect enters the Composition, it launches a coroutine with the block of code passed as a parameter.
        The coroutine will be cancelled if LaunchedEffect leaves the composition.
        If LaunchedEffect is recomposed with different keys (see the Restarting Effects section below),
        the existing coroutine will be cancelled and the new suspend function will be launched in a new coroutine.
     */
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }))

        delay(2000L)
        // we keep the default city blank so that we make a call based on coordinates if know in the WeatherMainScreen
        val defaultCity = " "
        navController.navigate(WeatherScreens.MainScreen.name+"/$defaultCity")
    } )

    //Splash screen canvas with image and text
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.Gray,
        border = BorderStroke(
            width = 2.dp, color = Color.LightGray
        )
    ) {
        Column(modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.sun),
                contentDescription = "sunny icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(95.dp))
            Text(text = "Weather Tracker",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray)
        }
    }
}