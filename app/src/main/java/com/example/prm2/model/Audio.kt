package com.example.prm2.model

data class Audio (
    var url: String? = null,
    var lengthSec: Int? = null
) {
    companion object {
        fun lengthAsTimeString(len: Int): String {
                val minutes = (len / 60).let { if (it < 10) "0$it" else it.toString() }
                val seconds = (len % 60).let { if (it < 10) "0$it" else it.toString() }
                return "$minutes:$seconds"

        }
    }
}