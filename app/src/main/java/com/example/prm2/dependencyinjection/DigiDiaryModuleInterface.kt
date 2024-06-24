package com.example.prm2.dependencyinjection

interface DigiDiaryModuleInterface{
    val audioRecorder: AudioRecorder
    val imageCanvas: ImageCanvas
    val permissionsManager: PermissionsManager
    val firebaseDB: FirebaseDB
    val locationServiceProvider: LocationServiceProvider
}