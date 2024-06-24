package com.example.prm2.viewmodel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.prm2.dependencyinjection.PermissionsManager
import com.example.prm2.model.Entry
import com.google.android.gms.maps.model.LatLng

class ProvidableCompositionLocalValues {
    companion object {
        val keyword = compositionLocalOf<String> {
            error("No keyword provided")
        }
        val setKeyword = staticCompositionLocalOf<(keyword: String) -> Unit> {
            error("No keyword provided")
        }

        val entries = compositionLocalOf<MutableMap<String, Entry>> {
            error("No SharedViewModel provided")
        }
        val saveEntry = staticCompositionLocalOf<(
            entry: Entry,
            key: String?
        ) -> Unit> {
            error("No FirebaseDB provided")
        }
        val deleteEntry = staticCompositionLocalOf<(key: String) -> Unit> {
            error("No FirebaseDB provided")
        }

        val permissions = compositionLocalOf<PermissionsManager.Permissions> {
            error("No PermissionsManager provided")
        }

        val currentLocation = compositionLocalOf<LatLng> {
            error("No location provided")
        }

        val getLocationName = staticCompositionLocalOf<
                    (latLng: LatLng) -> String
                > {
            error("No location provided")
        }

        val getCountryName = staticCompositionLocalOf<
                    (latLng: LatLng) -> String
                > {
            error("No location provided")
        }

    }
}