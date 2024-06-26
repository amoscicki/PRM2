package com.example.prm2.viewmodel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.prm2.dependencyinjection.AudioRecorder
import com.example.prm2.dependencyinjection.FirebaseDB
import com.example.prm2.dependencyinjection.PermissionsManager
import com.example.prm2.model.Entry
import com.google.android.gms.maps.model.LatLng
import java.io.File

class ProvidableCompositionLocalValues {
    companion object {
        val LocalKeyword = compositionLocalOf<String> {
            error("No keyword provided")
        }
        val LocalSetKeyword = staticCompositionLocalOf<(keyword: String) -> Unit> {
            error("No keyword provided")
        }

        val LocalEntries = compositionLocalOf<MutableMap<String, Entry>> {
            error("No SharedViewModel provided")
        }
        val LocalSaveEntry = staticCompositionLocalOf<(
            entry: Entry,
            key: String?,
            audio: File?,
            audioLength: Int?,
            image: File?
        ) -> Unit> {
            error("No FirebaseDB provided")
        }
        val LocalDeleteEntry = staticCompositionLocalOf<(key: String) -> Unit> {
            error("No FirebaseDB provided")
        }

        val LocalPermissions = compositionLocalOf<PermissionsManager.Permissions> {
            error("No PermissionsManager provided")
        }

        val LocalCurrentLocation = compositionLocalOf<LatLng> {
            error("No location provided")
        }

        val LocalGetLocationName = staticCompositionLocalOf<(latLng: LatLng) -> String> {
            error("No location provided")
        }

        val LocalGetCountryName = staticCompositionLocalOf<(latLng: LatLng) -> String> {
            error("No location provided")
        }

        val LocalAudioRecorder = staticCompositionLocalOf<AudioRecorder> {
            error("No audio recorder provided")
        }

        val LocalUploadFile = staticCompositionLocalOf<(
            file: File, type: FirebaseDB.FileType, callback: (String) -> Unit
        ) -> Unit> {
            error("No file upload provided")
        }

        val LocalTempImageFile = compositionLocalOf<File?> {
            error("No image file provided")
        }
        val LocalSetTempImageFile = staticCompositionLocalOf<(file: File?) -> Unit> {
            error("No image file provided")
        }

        val LocalTempAudioFile = compositionLocalOf<File?> {
            error("No audio file provided")
        }

        val LocalSetTempAudioFile = staticCompositionLocalOf<(file: File?) -> Unit> {
            error("No audio file provided")
        }

        val LocalTempAudioSeconds = compositionLocalOf<Int> {
            error("No audio seconds provided")
        }

        val LocalSetTempAudioSeconds = staticCompositionLocalOf<(seconds: Int) -> Unit> {
            error("No audio seconds provided")
        }


    }
}