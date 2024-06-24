package com.example.prm2.dependencyinjection

import com.example.prm2.model.Audio
import com.example.prm2.model.Entry
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import java.util.Date

class FirebaseDB {
    private val db by lazy {
        Firebase.firestore
    }

    init {
        seedDatabase(db)
    }


    fun listen(
        collectionName: String,
        callback: (QuerySnapshot?) -> Unit
    ): ListenerRegistration {
        return db.collection("entries").addSnapshotListener { value, error ->
            if (error != null) {
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            callback(value)
        }
    }


    private fun seedDatabase(db: FirebaseFirestore) {

         db.collection("entries").get().addOnSuccessListener {

            val entry = Entry(
                date = Date(),
                geo = LatLng(51.5, 0.0),
                audioUrls = listOf(
                    Audio("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", 60),
                ),
            )
            if (it.documents.size == 0)
                for (i in 1..10) {
                    entry.title = "Entry $i"
                    entry.geo = LatLng(
                        (entry.geo!!.latitude.plus(i*0.2)),
                        entry.geo!!.longitude.minus(i*0.2)
                    )
                    db.collection("entries").add(entry)
                }


        }

    }



    fun saveEntry(entry: Entry, key: String? = null) {
        if (key != null) {
            db.collection("entries").document(key).set(entry)
            return
        }
        db.collection("entries").add(entry.toFirestoreObject())
    }

    fun deleteEntry(key: String) {
        db.collection("entries").document(key).delete()
    }
}