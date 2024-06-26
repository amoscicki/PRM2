package com.example.prm2.dependencyinjection

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresPermission
import java.io.File
import java.io.IOException

@Suppress("DEPRECATION")
class AudioRecorder(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var startTime: Long = 0
    private lateinit var outputFile: File

    @RequiresPermission(android.Manifest.permission.RECORD_AUDIO)
    fun startRecording() {
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

        outputFile = createOutputFile()

        mediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)

            try {
                prepare()
                start()
                startTime = System.currentTimeMillis()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresPermission(android.Manifest.permission.RECORD_AUDIO)
    fun stopRecording(): Pair<File, Int> {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        val duration = ((System.currentTimeMillis() - startTime) / 1000).toInt()
        return Pair(outputFile, duration)
    }

    fun startPlaying(file: File) {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(file.absolutePath)
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun pausePlaying() {
        mediaPlayer?.pause()
    }

    fun resumePlaying() {
        mediaPlayer?.start()
    }

    fun stopPlaying() {
        mediaPlayer?.apply {
            stop()
            reset()
            release()
        }
        mediaPlayer = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }

    private fun createOutputFile(): File {
        val fileName = "AUDIO_${System.currentTimeMillis()}.mp3"
        return File(context.externalCacheDir, fileName)
    }
}