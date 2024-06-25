package com.example.prm2.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.prm2.dependencyinjection.LocationServiceProvider
import com.example.prm2.viewmodel.LocationViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("DEPRECATION")
class LocationService : Service() {

    private val serviceScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )
    private lateinit var locationServiceProvider: LocationServiceProvider

    private val locationViewModel = LocationViewModel.getInstance()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        locationServiceProvider = LocationServiceProvider(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                start()
            }

            ACTION_STOP -> {
                stop()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("MissingPermission")
    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("DigiDiaryLocationService")
            .setContentText("Current location: null")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        locationServiceProvider.startLocationUpdates().onEach { loc ->
            val lat = loc.latitude
            val lon = loc.longitude
            val newNotification = notification.setContentText("Current location: $lat, $lon")
            locationViewModel.updateLocation(loc)
            notificationManager.notify(1, newNotification.build())

        }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

}