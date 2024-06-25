package com.example.prm2.ui.screens.audio

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalStartRecording

@Composable
fun RecordingDialog(modifier: Modifier = Modifier ) {
    val recording = remember { mutableStateOf(false) }

    val startRecording = LocalStartRecording.current
    val stopRecording = LocalStartRecording.current

    fun handleRecording() {
        if (recording.value) {
            stopRecording()
        } else {
            startRecording()
        }
        recording.value = !recording.value
    }

    IconButton(onClick = {
        handleRecording()
    }){
        Icon(
            if(recording.value) Icons.Outlined.MicOff else Icons.Outlined.Mic,
            null)
    }
}