package com.example.prm2.viewmodel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
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
            key: String?
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

        val LocalGetLocationName = staticCompositionLocalOf<
                    (latLng: LatLng) -> String
                > {
            error("No location provided")
        }

        val LocalGetCountryName = staticCompositionLocalOf<
                    (latLng: LatLng) -> String
                > {
            error("No location provided")
        }

        val LocalStartRecording = staticCompositionLocalOf<() -> Unit> {
            error("No audio recorder provided")
        }

        val LocalStopRecording = staticCompositionLocalOf<() -> Pair<File,Int>> {
            error("No audio recorder provided")
        }


    }
}