package com.example.prm2.model

data class Audio (
    var url: String? = null,
    var lengthSec: Int? = null
) {
    fun lengthAsTimeString(): String? {
        return lengthSec?.let { len ->
            val minutes = (len / 60).let { if (it < 10) "0$it" else it.toString() }
            val seconds = (len % 60).let { if (it < 10) "0$it" else it.toString()}
            return "$minutes:$seconds"
        }
    }
}