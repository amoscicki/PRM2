package com.example.prm2.model


import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class Entry(
    var title: String? = null,
    var note: String? = null,
    @ServerTimestamp
    var date: Date? = null,
    var audio: Audio? = null,
    var imageUrl: String? = null,
    var geo: LatLng? = null,
) {
    fun toFirestoreObject(): Map<String, Any?> {
        return mapOf(
            "title" to this.title,
            "note" to this.note,
            "date" to this.date,
            "audio" to this.audio?.let {
                mapOf(
                    "url" to it.url,
                    "duration" to it.lengthSec
                )
            },
            "imageUrl" to this.imageUrl,
            "geo" to this.geo?.let { GeoPoint(it.latitude, it.longitude) }
        )
    }
}