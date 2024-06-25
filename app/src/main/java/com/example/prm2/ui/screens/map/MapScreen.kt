package com.example.prm2.ui.screens.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.prm2.model.Entry
import com.example.prm2.ui.screens.entrylist.EntryCard
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalCurrentLocation
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalEntries
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(modifier: Modifier, navController: NavHostController) {
    val entries = LocalEntries.current
    val startLocation = LocalCurrentLocation.current
    val selectedEntry = mutableStateOf<Entry?>(null)

    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        if (selectedEntry.value != null) {
            EntryCard(entry = selectedEntry.value!!) { }
        }

    }) {
        GoogleMap(
           cameraPositionState = rememberCameraPositionState {
               position = CameraPosition.fromLatLngZoom(startLocation, 15f)
           },
        ) {
            Marker(
                state = rememberMarkerState(
                    position = startLocation,
                ),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                title = "Current Location",
            )
            entries.forEach { (key, entry) ->
                Marker(
                    state = rememberMarkerState(
                        position = entry.geo ?: LatLng(52.0, 0.0),
                    ),
                    title = entry.title,
                    onClick = {
                        selectedEntry.value = entries[key]
                        false
                    },
                )
            }
        }
    }
}