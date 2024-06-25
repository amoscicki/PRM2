package com.example.prm2.dependencyinjection

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color.RED
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset

class ImageCanvas(private val context: Context) {
    companion object {
        fun addTextToBitmap(bitmap: Bitmap, text: String, position: Offset): Bitmap {
            val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = android.graphics.Canvas(mutableBitmap)
            val paint = Paint().apply {
                color = RED
                textSize = 50f
                isAntiAlias = true
            }
            canvas.drawText(text, position.x, position.y, paint)
            return mutableBitmap
        }
    }
}