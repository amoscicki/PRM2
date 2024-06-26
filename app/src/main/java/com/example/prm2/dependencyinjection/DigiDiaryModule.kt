package com.example.prm2.dependencyinjection

import android.content.Context

class DigiDiaryModule(
    private val context: Context
) : DigiDiaryModuleInterface {

    override val audioRecorder: AudioRecorder by lazy {
        AudioRecorder(context)
    }

    override val permissionsManager: PermissionsManager by lazy {
        PermissionsManager(context)
    }

    override val firebaseDB: FirebaseDB by lazy {
        FirebaseDB()
    }

    override val locationServiceProvider: LocationServiceProvider by lazy {
        LocationServiceProvider(context)
    }


}
