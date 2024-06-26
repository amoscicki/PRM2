package com.example.prm2.dependencyinjection

interface DigiDiaryModuleInterface{
    val audioRecorder: AudioRecorder
    val permissionsManager: PermissionsManager
    val firebaseDB: FirebaseDB
    val locationServiceProvider: LocationServiceProvider
}