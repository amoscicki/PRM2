package com.example.prm2.ui.screens.entrylist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Undo
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.prm2.R
import com.example.prm2.model.Audio
import com.example.prm2.model.Entry
import com.example.prm2.ui.DialogButton
import com.example.prm2.ui.screens.audio.RecordingComponent
import com.example.prm2.ui.screens.picture.ImageComponent
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalCurrentLocation
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalDownloadFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetCountryName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetLocationName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSaveEntry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempAudioSeconds
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempImageFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempAudioSeconds
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempImageFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable
fun QuickEditEntryCard(
    entry: MutableState<Entry?>,
    key: MutableState<String?>,
) {
    val saveEntry = LocalSaveEntry.current
    val currentLocation = LocalCurrentLocation.current
    val fm = LocalFocusManager.current
    val tempImage = LocalTempImageFile.current
    val setTempImage = LocalSetTempImageFile.current
    val tempAudio = LocalTempAudioFile.current
    val setTempAudio = LocalSetTempAudioFile.current
    val tempAudioLength = LocalTempAudioSeconds.current
    val setTempAudioLength = LocalSetTempAudioSeconds.current
    val downloadFile = LocalDownloadFile.current
    val location = mutableStateOf(entry.value?.geo ?: currentLocation)

    val date = mutableStateOf(entry.value?.date ?: Date())

    val title = mutableStateOf(entry.value?.title ?: "")

    val note = mutableStateOf(entry.value?.note ?: "")

    val image = mutableStateOf(entry.value?.imageUrl ?: "")

    val audio = mutableStateOf(entry.value?.audio ?: Audio())

    LaunchedEffect(key,entry) {
        if (key.value != null && entry.value?.audio?.url != null) {
            downloadFile(entry.value?.audio?.url!!, setTempAudio)
            setTempAudioLength(entry.value?.audio?.lengthSec!!)
        }
        if (key.value != null && entry.value?.imageUrl != null) {
            downloadFile(entry.value?.imageUrl!!, setTempImage)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    text = stringResource(
                        R.string.quick_entry,
                        if (entry.value == null) stringResource(R.string.create) else stringResource(
                            R.string.edit
                        )
                    ),
                    modifier = Modifier.padding(16.dp, 16.dp),
                    style = MaterialTheme.typography.labelSmall
                )

                if (entry.value != null)
                    IconButton(modifier = Modifier, onClick = {
                        entry.value = null
                        key.value = null
                    }) {
                        Icon(
                            Icons.AutoMirrored.Outlined.Undo,
                            contentDescription = null,
                            tint = Color.Red
                        )

                    }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.EditLocationAlt, contentDescription = null)
                    }
                    Text(text = location.value.let {
                        "${LocalGetLocationName.current(it)} ${
                            LocalGetCountryName.current(
                                it
                            )
                        }"
                    })
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = date.value.let {
                        SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                        ).format(it)
                    })
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.EditCalendar, contentDescription = null)
                    }

                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text(stringResource(R.string.title_goes_here)) })

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                    minLines = 3,
                    value = note.value,
                    onValueChange = { note.value = it },
                    placeholder = { Text("\n\nWrite your thoughts here...") })

            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {


                    DialogButton(
                        icon = Icons.Outlined.Image, contentDescription = stringResource(R.string.manage_picture),
                        defaultPadding = false,
                        highlight = LocalTempImageFile.current != null
                    ) {
                        ImageComponent(modifier = Modifier)

                    }

                    DialogButton(
                        icon = Icons.Outlined.LibraryMusic,
                        contentDescription = stringResource(R.string.manage_audio_recording),
                        highlight = LocalTempAudioFile.current != null
                    ) {
                        RecordingComponent()
                    }

                }

                IconButton(onClick = {
                    saveEntry(
                        Entry(
                            date = date.value,
                            title = title.value,
                            note = note.value,
                            geo = location.value,
                            imageUrl = image.value,
                            audio = audio.value
                        ),
                        key.value,
                        tempAudio,
                        tempAudioLength,
                        tempImage
                    )
                    entry.value = null
                    key.value = null
                    setTempAudio(null)
                    setTempImage(null)
                    setTempAudioLength(0)

                    fm.clearFocus(true)
                }) {

                    Icon(Icons.Outlined.Save, contentDescription = null)
                }
            }


        }
    }
}

