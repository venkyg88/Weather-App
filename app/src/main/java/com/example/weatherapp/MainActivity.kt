package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.weatherapp.model.LocationCoordinates
import com.example.weatherapp.ui.ApplicationViewModel
import com.example.weatherapp.ui.navigation.WeatherNavigation
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val applicationViewModel : ApplicationViewModel by viewModels<ApplicationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val location by applicationViewModel.getLocationLiveData().observeAsState()
            prepLocationUpdates()
            WeatherApp(location)
        }
    }
    private fun prepLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            requestSinglePermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestSinglePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted ->
        if (isGranted) {
            requestLocationUpdates()
        } else {
            Toast.makeText(this, "GPS Unavailable", Toast.LENGTH_LONG).show()
        }
    }

    private fun requestLocationUpdates() {
        applicationViewModel.startLocationUpdates()
    }
}

@Composable
fun WeatherApp(location: LocationCoordinates?){
    WeatherAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background) {
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherNavigation(location)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {

    }
}