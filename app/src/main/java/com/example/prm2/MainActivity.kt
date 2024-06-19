package com.example.prm2


import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.prm2.model.Entry
import com.example.prm2.ui.Content
import com.example.prm2.ui.Entry
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import java.util.Date


class MainActivity : ComponentActivity() {
    private val db: FirebaseFirestore by lazy { Firebase.firestore }
    private val entries : MutableList<Entry> = mutableListOf()
    private lateinit var datalistener: ListenerRegistration

    // Get/check biometric/pin or prompt to set it up
    // load home screen -> get data from FB
    // function for adding new entry
    // get location
    // get date
    // function for adding picture
    // screen for editing picture
    // function for adding audio

    // function to edit entry -> change component to edit mode
    // function to delete entry
    // function to play audio
    // function to show map -> screen with map
    // search functionality
    //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        seedDatabase(db)

        setContent {
            Content()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        datalistener.remove()
    }

    override fun onResume() {
        super.onResume()
        listen()
    }

    override fun onPause() {
        super.onPause()
        datalistener.remove()
    }

    private fun listen() {
        datalistener = db.collection("entries").addSnapshotListener { value, error ->
            if (error != null) {
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (value != null) {
                for (doc in value) {
                    val entry = doc.toObject(Entry::class.java)
                    entries.add(entry)
                    println(entry)
                }
            }
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(
                    it.windowToken,
                    0
                )
        }
        return super.dispatchTouchEvent(ev)
    }

}

fun seedDatabase(db: FirebaseFirestore) {

    val entries = db.collection("entries").get().addOnSuccessListener {

        val entry: Entry = Entry(
            date = Date(),
            geo = "Warsaw, Poland"
        )
        if (it.documents.size == 0)
            db.collection("entries").add(entry)

    }

}

