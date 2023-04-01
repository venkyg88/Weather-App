package com.example.weatherapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(application: Application,
                        private val repository: WeatherRepository): AndroidViewModel(application){

    private val locationLiveData = LocationLiveData(application)
    fun getLocationLiveData() = locationLiveData
    fun startLocationUpdates() {
        locationLiveData.startLocationUpdates()
    }

}