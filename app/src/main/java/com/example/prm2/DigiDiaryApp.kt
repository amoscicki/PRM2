package com.example.prm2

import android.app.Application
import com.example.prm2.dependencyinjection.DigiDiaryModule

class DigiDiaryApp : Application() {

    companion object {
        lateinit var instance: DigiDiaryModule
    }

    override fun onCreate() {
        super.onCreate()
        instance = DigiDiaryModule(this)
    }
}