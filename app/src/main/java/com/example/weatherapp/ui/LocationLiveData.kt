package com.example.weatherapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.example.weatherapp.model.LocationCoordinates
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(var context: Context): LiveData<LocationCoordinates>() {
    // We need this to get the last know location in the onActive method
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // which means we have at least one observer or component looking at us
    override fun onActive() {
        super.onActive()
        // Request permission from the user
        if(ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )  {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location -> location.also {
            setLocationData(it)
        }
        }
        startLocationUpdates()
    }

    internal fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    /*
    The reason we are storing the lat and lon in string because what goes after decimal is important.
    Sometimes there could be issues with Float or Double when performing arithmetic operations
     */
    /*
        Live data is a data that can change and be observed when it changes, that data that changes
        is stored in an attibute value below, everytime this fun is invoked LocationCoordinates object
        is updated and assigns to value, which is an attibure of a super class, anyone oberving on that will
        be aware that location has changed
     */
    private fun setLocationData(location: Location?) {
        location?.let { location ->
            value = LocationCoordinates(location.longitude.toString(), location.latitude.toString())
        }
    }

    // which means we don't have anything looking at us
    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    companion object {
        val ONE_DAY: Long = 1440000
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = ONE_DAY
            fastestInterval = ONE_DAY / 2
            priority = LocationRequest.PRIORITY_LOW_POWER

        }
    }

}