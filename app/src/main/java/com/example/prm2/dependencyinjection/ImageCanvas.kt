package com.example.prm2.dependencyinjection

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color.RED
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset

class ImageCanvas(private val context: Context) {
//    private val cameraController = LifecycleCameraController(context)
//    private val executor: Executor = ContextCompat.getMainExecutor(context)
//
//    @Composable
//    fun CameraView(onImageCaptured: (Bitmap) -> Unit) {
//        val lifecycleOwner = LocalLifecycleOwner.current
//
//        DisposableEffect(lifecycleOwner) {
//            cameraController.bindToLifecycle(lifecycleOwner)
//            onDispose {
//                // Clean up resources if needed
//            }
//        }
//
//        Box(modifier = Modifier.fillMaxSize()) {
//            AndroidView(
//                factory = { context ->
//                    PreviewView(context).apply {
//                        controller = cameraController
//                    }
//                },
//                modifier = Modifier.fillMaxSize()
//            )
//
//            Button(
//                onClick = {
//                    lifecycleOwner.lifecycleScope.launch {
//                        try {
//                            val bitmap = takePhoto()
//                            onImageCaptured(bitmap)
//                        } catch (e: Exception) {
//                            // Handle the error, maybe show a message to the user
//                            e.printStackTrace()
//                        }
//                    }
//                },
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 16.dp)
//            ) {
//                Text("Take Picture")
//            }
//        }
//    }
//
//    private suspend fun takePhoto(): Bitmap = withContext(Dispatchers.IO) {
//        suspendCoroutine { continuation ->
//            val imageCapture = ImageCapture.Builder()
//                .setTargetRotation(cameraController.cameraInfo?.sensorRotationDegrees ?: 0)
//                .build()
//
//            val outputOptions = ImageCapture.OutputFileOptions.Builder(createTempFile()).build()
//
//            imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    outputFileResults.savedUri?.let { uri ->
//                        val bitmap = android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//                        continuation.resume(bitmap)
//                    } ?: continuation.resumeWithException(Exception("Failed to save image"))
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//                    continuation.resumeWithException(exception)
//                }
//            })
//        }
//    }
//
//    private fun createTempFile(): File {
//        val storageDir: File? = context.getExternalFilesDir(null)
//        return File.createTempFile("temp_image", ".jpg", storageDir)
//    }
//
//

    //
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