package com.example.prm2.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm2.dependencyinjection.AudioRecorder
import com.example.prm2.dependencyinjection.FirebaseDB
import com.example.prm2.dependencyinjection.ImageCanvas
import com.example.prm2.dependencyinjection.LocationServiceProvider
import com.example.prm2.dependencyinjection.PermissionsManager
import com.example.prm2.extensions.toEntry
import com.example.prm2.model.Entry
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

class DigiDiaryViewModel(
    private val firebaseDB: FirebaseDB,
    permissionsManager: PermissionsManager,
    imageCanvas: ImageCanvas,
    audioRecorder: AudioRecorder,
    private val locationServiceProvider: LocationServiceProvider
) : ViewModel() {
    private lateinit var datalistener: ListenerRegistration


    val keyword = mutableStateOf("")
    val setKeyword = { kw: String ->
        keyword.value = kw
        applyFilters()
        println("Keyword: $keyword")
    }

    val permissions = permissionsManager.state

    private val fetchedEntries: MutableMap<String, Entry> = mutableMapOf()
    val entries: MutableMap<String, Entry> = mutableStateMapOf()
    val saveEntry = firebaseDB::saveEntry
    val deleteEntry = firebaseDB::deleteEntry

    private fun applyFilters() {
        if (keyword.value.isEmpty()) {
            entries.clear()
            entries.putAll(fetchedEntries)
            println(entries)
            return
        }
        fetchedEntries.filterValues { e ->
            e.title?.contains(
                keyword.value,
                ignoreCase = true
            ) ?: false
        }.let {
            entries.clear()
            entries.putAll(it)
            println(entries)
        }

    }


    val currentLocation = mutableStateOf<LatLng>(LatLng(51.5, 0.0))
    val getLocationName = locationServiceProvider::getLocationName
    val getCountryName = locationServiceProvider::getCountryName

    val startRecording = audioRecorder::startRecording
    val stopRecording = audioRecorder::stopRecording

    init {
        datalistener = firebaseDB.listen("entries") { qs ->
            keyword.value = ""
            fetchedEntries.clear()
            qs?.documents?.forEach { doc ->
                fetchedEntries[doc.id] = doc.toEntry()!!
            }
            applyFilters()
        }
        viewModelScope.launch {
            locationServiceProvider.startLocationUpdates().collect { location ->
                currentLocation.value = LatLng(location.latitude, location.longitude)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        datalistener.remove()
        locationServiceProvider.stopLocationUpdates()
    }

}





