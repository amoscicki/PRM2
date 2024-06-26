package com.example.prm2.ui.screens.picture

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.prm2.extensions.createTempPictureUri
import com.example.prm2.extensions.saveBitmapToFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempImageFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempImageFile
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageComponent(modifier: Modifier) {
    val context = LocalContext.current
    var currentPhotoUri by remember { mutableStateOf(value = Uri.EMPTY) }
    var tempPhotoUri by remember { mutableStateOf(value = Uri.EMPTY) }
    val imageFile = LocalTempImageFile.current
    val setImageFile = LocalSetTempImageFile.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                currentPhotoUri = tempPhotoUri
                setImageFile(BitmapFactory.decodeStream(
                    context.contentResolver.openInputStream(currentPhotoUri)
                ).saveBitmapToFile(context))
            }
        })
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
        onPermissionResult = { granted ->
            if (granted) {
                tempPhotoUri = context.createTempPictureUri()
                cameraLauncher.launch(tempPhotoUri)
            } else print("camera permission is denied")
        })




    Column(modifier = modifier.fillMaxWidth()) {
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            cameraPermissionState.launchPermissionRequest()
        }) {
            Text("Take Picture")

        }
        Column(
            Modifier.fillMaxWidth()
        ) {

            if (imageFile != null) {

                ImageEditor(modifier = Modifier.padding(16.dp))
            }

        }
    }

}

