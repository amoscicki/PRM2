package com.example.prm2.extensions

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.createTempPictureUri(): Uri {
    val tempFile = File.createTempFile(
        "${System.currentTimeMillis()}", ".png", cacheDir
    ).apply {
        createNewFile()
    }

    return FileProvider.getUriForFile(
        applicationContext, applicationContext.packageName + ".provider", tempFile
    )
}