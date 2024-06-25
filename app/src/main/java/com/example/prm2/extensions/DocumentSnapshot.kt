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
            audioUrls = (data["audioUrls"] as? List<Map<String, Any>>)?.map { audioMap ->
                Audio(
                    url = audioMap["url"] as? String,
                    lengthSec = (audioMap["lengthSec"] as? Number)?.toInt()
                )
            } ?: emptyList(),
            imageUrls = (data["imageUrls"] as? List<String>) ?: emptyList(),
            geo = (data["geo"] as? Map<String,Double>)?.let { LatLng(it["latitude"] as Double, it["longitude"] as Double) }
        )
    }
}
//
//class Serializers {
//    companion object {
//        fun toEntry(document: DocumentSnapshot): Entry? {
//            return document.data?.let { data ->
//                Entry(
//                    content = data["content"] as? String,
//                    date = (data["date"] as? Timestamp)?.toDate(),
//                    audioUrls = (data["audioUrls"] as? List<Map<String, Any>>)?.map { audioMap ->
//                        Audio(
//                            url = audioMap["url"] as? String,
//                            lengthSec = (audioMap["lengthSec"] as? Number)?.toInt()
//                        )
//                    } ?: emptyList(),
//                    imageUrls = (data["imageUrls"] as? List<String>) ?: emptyList(),
//                    geo = (data["geo"] as? GeoPoint)?.let { LatLng(it.latitude, it.longitude) }
//                )
//            }
//        }
//
//        fun toFirestoreObject(entry: Entry): Map<String, Any?> {
//            return mapOf(
//                "content" to entry.content,
//                "date" to entry.date,
//                "audioUrls" to entry.audioUrls.map { audio ->
//                    mapOf(
//                        "url" to audio.url,
//                        "lengthSec" to audio.lengthSec
//                    )
//                },
//                "imageUrls" to entry.imageUrls,
//                "geo" to entry.geo?.let { GeoPoint(it.latitude, it.longitude) }
//            )
//        }
//    }
//}
//
