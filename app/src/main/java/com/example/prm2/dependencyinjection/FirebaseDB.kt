package com.example.prm2.dependencyinjection

import androidx.core.net.toUri
import com.example.prm2.model.Audio
import com.example.prm2.model.Entry
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.File
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
                imageUrl = "https://cdn.britannica.com/89/187589-050-E8D5A657/Workers-Big-Ben-London.jpg")
            if (it.documents.size == 0)
                for (i in 1..10) {
                    entry.title = "Entry $i"
                    entry.note = "This is entry $i \n lorem ipsum dolor sit amet \n lorem ipsum dolor sit amet \n lorem ipsum dolor sit amet"
                    entry.geo = LatLng(
                        (entry.geo!!.latitude.plus(i * 0.2)),
                        entry.geo!!.longitude.minus(i * 0.2)
                    )
                    db.collection("entries").add(entry)
                }
        }
    }

    fun saveEntry(
        entry: Entry,
        key: String? = null,
        audio: File?,
        audioLength: Int?,
        image: File?
    ) {
        val audioGiven = audio != null && audioLength != null
        val imageGiven = image != null

        fun uploadFiles(key: String) {
            if (!audioGiven && !imageGiven) {
                db.collection("entries").document(key).set(entry)
                return
            }
            if (audioGiven) {
                uploadFile(audio!!, FileType.AUDIO) {
                    entry.audio = Audio(
                        url = it,
                        lengthSec = audioLength
                    )
                    db.collection("entries").document(key).set(entry)
                }
            }
            if (imageGiven) {
                uploadFile(image!!, FileType.IMAGE) {
                    entry.imageUrl = it
                    db.collection("entries").document(key).set(entry)
                }
            }
        }

        if (key != null) {
            uploadFiles(key)
            return
        }

        db.collection("entries").add(entry.toFirestoreObject()).addOnSuccessListener {
            uploadFiles(it.id)
        }

    }

    fun deleteEntry(key: String) {
        db.collection("entries").document(key).delete()
    }

    fun uploadFile(
        file: File,
        type: FileType,
        callback: (String) -> Unit
    ) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val fileRef = storageRef.child(type.toString() + '/' + file.name)
        fileRef.putFile(file.toUri()).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener {
                callback(it.toString())
            }
        }
    }

    fun downloadFile(fileUrl: String, callback: (File) -> Unit) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val fileRef = storageRef.child(fileUrl)
        val localFile = File.createTempFile("temp", "file")
        fileRef.getFile(localFile).addOnSuccessListener {
            callback(localFile)
        }
    }


    enum class FileType {
        AUDIO, IMAGE
    }
}