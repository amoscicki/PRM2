package com.example.prm2.extensions

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

fun Bitmap.saveBitmapToFile(context: Context): File {
    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    FileOutputStream(file).use { out ->
        this.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
    return file
}