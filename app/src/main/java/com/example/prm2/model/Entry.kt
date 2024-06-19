package com.example.prm2.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Entry (
    var content: String? = null,
    @ServerTimestamp
    var date: Date? = null,
    var audioUrls: List<Audio> = emptyList(),
    var imageUrls: List<String> = emptyList(),
    var geo: String? = null,
)