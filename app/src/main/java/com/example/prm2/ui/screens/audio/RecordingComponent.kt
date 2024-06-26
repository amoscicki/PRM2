package com.example.prm2.ui.screens.audio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prm2.model.Audio
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalAudioRecorder
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempAudioSeconds
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempAudioSeconds
import kotlinx.coroutines.delay
import java.lang.Math.floorDiv

@Composable
fun RecordingComponent(
    modifier: Modifier = Modifier
) {
    val audioRecorder = LocalAudioRecorder.current
    val currentFile = LocalTempAudioFile.current
    val setCurrentFile = LocalSetTempAudioFile.current
    val totalTime = LocalTempAudioSeconds.current
    val setTotalTime = LocalSetTempAudioSeconds.current
    var isRecording by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var currentTime by remember { mutableStateOf(0) }


    LaunchedEffect(isPlaying, isPaused) {
        while (isPlaying && !isPaused) {
            progress = audioRecorder.getCurrentPosition().toFloat() / audioRecorder.getDuration()
            currentTime = floorDiv(audioRecorder.getCurrentPosition(), 1000) + 1
            isPlaying = audioRecorder.isPlaying()
            delay(100) // Update progress every 100ms
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Recording controls
        Button(
            onClick = {
                if (isRecording) {
                    val (file, duration) = audioRecorder.stopRecording()
                    setCurrentFile(file)
                    setTotalTime(duration)
                    isRecording = false
                } else {
                    audioRecorder.startRecording()
                    isRecording = true
                    currentTime = 0
                    setTotalTime(0)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                if (isRecording) Icons.Filled.Stop else Icons.Filled.Mic,
                contentDescription = if (isRecording) "Stop Recording" else "Start Recording"
            )
            Spacer(Modifier.width(8.dp))
            Text(if (isRecording) "Stop Recording" else "Start Recording")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress bar and time
        if (isPlaying || isPaused || currentFile != null) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(Audio.lengthAsTimeString(currentTime))
                Text(Audio.lengthAsTimeString(totalTime))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Playback controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    if (isPlaying) {
                        if (isPaused) {
                            audioRecorder.resumePlaying()
                            isPaused = false
                        } else {
                            audioRecorder.pausePlaying()
                            isPaused = true
                        }
                    } else {
                        currentFile?.let {
                            audioRecorder.startPlaying(it)
                            isPlaying = true
                            isPaused = false
                        }
                    }
                },
                enabled = currentFile != null
            ) {
                Icon(
                    if (isPlaying && !isPaused) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isRecording) "Stop Recording" else "Start Recording"
                )
            }

            IconButton(
                onClick = {
                    audioRecorder.stopPlaying()
                    isPlaying = false
                    isPaused = false
                    progress = 0f
                    currentTime = 0
                },
                enabled = isPlaying || isPaused
            ) {
                Icon(Icons.Filled.Stop, contentDescription = "Stop")
            }

            IconButton(
                onClick = {
                    setCurrentFile(null)
                    isPlaying = false
                    isPaused = false
                    progress = 0f
                    currentTime = 0
                    setTotalTime(0)
                },
                enabled = currentFile != null
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Discard Recording")
            }
        }


    }
}