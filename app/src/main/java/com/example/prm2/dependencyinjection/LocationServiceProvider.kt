package com.example.prm2.dependencyinjection

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

@Suppress("DEPRECATION")
class LocationServiceProvider(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val geoCoder by lazy { Geocoder(context, Locale.getDefault()) }


    val currentLocation = mutableStateOf<LatLng>(LatLng(51.5, 0.0))

    private val locationRequest = LocationRequest.create().apply {
        interval = 10000 // Update interval in milliseconds
        fastestInterval = 5000 // Fastest update interval in milliseconds
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    @RequiresPermission(allOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        currentLocation.value = LatLng(location.latitude, location.longitude)
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(object : LocationCallback() {})
    }

    private fun getAddressesList(latLng: LatLng): List<Address>? {
        return geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    }

    fun getLocationName(
        latLng:
        LatLng
    ): String {
               return getAddressesList(latLng).orEmpty().firstOrNull()
                   .let {
                       it?.locality ?: it?.subAdminArea ?: it?.adminArea
               }?.let { "$it \n" } ?: ""
    }

    fun getCountryName(latLng: LatLng): String {
        return getAddressesList(latLng).orEmpty().firstOrNull()?.countryName ?: "International"
    }

}