package com.example.prm2.model


import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class Entry (
    var title: String? = null,
    var content: String? = null,
    @ServerTimestamp
    var date: Date? = null,
    var audioUrls: List<Audio> = emptyList(),
    var imageUrls: List<String> = emptyList(),
    var geo: LatLng? = null,
){
    fun toFirestoreObject(): Map<String, Any?> {
        val entry = this
        return mapOf(
            "title" to entry.title,
            "content" to entry.content,
            "date" to entry.date,
            "audioUrls" to entry.audioUrls.map { audio ->
                mapOf(
                    "url" to audio.url,
                    "lengthSec" to audio.lengthSec
                )
            },
            "imageUrls" to entry.imageUrls,
            "geo" to entry.geo?.let { GeoPoint(it.latitude, it.longitude) }
        )
    }
}