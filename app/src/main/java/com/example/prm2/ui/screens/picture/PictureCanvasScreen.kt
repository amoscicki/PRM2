package com.example.prm2.ui.screens.picture

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun PictureCanvasScreen(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current

    var capturedImageUri = remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap: Bitmap? ->
            // Handle the returned Bitmap

        }
    )

    Button(onClick = {
        cameraLauncher.launch()
    }) {
        Text("Take Picture")
    }
//    val imageCanvas = remember { ImageCanvas(context) }
//    var capturedImage = remember { mutableStateOf<Bitmap?>(null) }
//
//    if (capturedImage == null) {
//        imageCanvas.CameraView { bitmap ->
//            capturedImage.value = bitmap
//        }
//    } else {
//        imageCanvas.ImageEditor(capturedImage.value!!)
//    }

}