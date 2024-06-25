package com.example.prm2.extensions

import com.example.prm2.model.Audio
import com.example.prm2.model.Entry
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toEntry(): Entry? {
    val document: DocumentSnapshot = this
    return document.data?.let { data ->
        Entry(
            title = data["content"] as? String,
            date = (data["date"] as? Timestamp)?.toDate(),
            audio = (data["audio"] as? Map<String, Audio>)?.let { audioMap ->
                Audio(
                    url = audioMap["url"] as? String,
                    lengthSec = (audioMap["lengthSec"] as? Number)?.toInt()
                )
            },
            imageUrl = data["imageUrl"] as? String,
            geo = (data["geo"] as? Map<String,Double>)?.let { LatLng(it["latitude"] as Double, it["longitude"] as Double) }
        )
    }
}