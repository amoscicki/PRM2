package com.example.prm2.ui.screens.picture

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.prm2.extensions.createTempPictureUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SandboxScreen(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    var currentPhotoUri by remember { mutableStateOf(value = Uri.EMPTY) }
    var tempPhotoUri by remember { mutableStateOf(value = Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) currentPhotoUri = tempPhotoUri
        })
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
        onPermissionResult = { granted ->
            if (granted) {
                tempPhotoUri = context.createTempPictureUri()
                cameraLauncher.launch(tempPhotoUri)
            } else print("camera permission is denied")
        })


    Column(modifier = modifier.fillMaxSize()) {
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            cameraPermissionState.launchPermissionRequest()
        }) {
            Text("Take Picture")

        }
        Column(
            Modifier.fillMaxSize()
        ) {
            if (currentPhotoUri != Uri.EMPTY) {
                val bitmap = BitmapFactory.decodeStream(
                    context.contentResolver.openInputStream(currentPhotoUri)
                )
                ImageEditor(bitmap)

            }

        }
    }

}

