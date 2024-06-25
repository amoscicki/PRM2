package com.example.prm2

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.prm2.dependencyinjection.DigiDiaryModule

class DigiDiaryApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: DigiDiaryModule
    }

    override fun onCreate() {
        super.onCreate()
        instance = DigiDiaryModule(this)
        val channel = NotificationChannel(
            "location",
            "DigiDiaryLocationService",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}