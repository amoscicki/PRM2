package com.example.prm2.dependencyinjection

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat

class PermissionsManager(val context: Context) {

    val state = mutableStateOf(
        Permissions(
            mapOf(
                "Camera" to Permission(
                    this,
                    "Allow app to use camera",
                    Manifest.permission.CAMERA
                ),
                "Location" to Permission(
                    this,
                    "Allow app to access device location",
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                "Microphone" to Permission(
                    this,
                    "Allow app to access device microphone",
                    Manifest.permission.RECORD_AUDIO
                )
            )
        )
    )


    data class Permission(
        val manager: PermissionsManager,
        val description: String,
        val permission: String,
    ) {
        val value = mutableStateOf(manager.getPermission(permission))

        fun requestPermission(launcher: ActivityResultLauncher<String>) {
            launcher.launch(permission)
        }
    }

    data class Permissions(
        val permissionsMap: Map<String, Permission>
    ) {
        @Composable
        fun map(action: @Composable (Map.Entry<String, Permission>) -> Unit) {
            permissionsMap.forEach {
                action(it)
            }
        }

        fun checkAllPermissionsGranted(): Boolean {
            return permissionsMap.values.all { it.value.value }
        }

    }


    fun getPermission(permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
        println("Permission $permission is $result")
        return result
    }




}