package com.example.prm2.ui.screens.picture

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import com.example.prm2.dependencyinjection.ImageCanvas.Companion.addTextToBitmap

@Composable
fun ImageEditor(bitmap: Bitmap) {
    var editedBitmap = remember { mutableStateOf(bitmap) }
    var text = remember { mutableStateOf("") }
    var textPosition = remember { mutableStateOf(Offset.Zero) }

    Column {
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        textPosition.value = offset
                    }
                }
        ) {
            drawImage(editedBitmap.value.asImageBitmap())
        }

        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("Enter text to add") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                editedBitmap.value = addTextToBitmap(editedBitmap.value, text.value, textPosition.value)
                text.value = ""
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Text to Image")
        }
    }
}