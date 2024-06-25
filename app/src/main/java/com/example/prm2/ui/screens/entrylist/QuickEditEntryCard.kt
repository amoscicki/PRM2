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
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prm2.model.Audio
import com.example.prm2.model.Entry
import com.example.prm2.ui.screens.audio.RecordingDialog
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalCurrentLocation
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetCountryName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetLocationName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSaveEntry
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

    val location = mutableStateOf(entry.value?.geo ?: currentLocation)

    val date = mutableStateOf(entry.value?.date ?: Date())

    val content = mutableStateOf(entry.value?.title ?: "")

    val imageList = mutableStateOf(entry.value?.imageUrl ?: "")

    val audioList = mutableStateOf(entry.value?.audio ?: Audio())



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
                    text = "Quick ${if (entry.value == null) "Create" else "Edit"} Entry",
                    modifier = Modifier.padding(16.dp, 16.dp),
                    style = MaterialTheme.typography.labelSmall
                )

                if (entry.value != null)
                    IconButton(modifier = Modifier, onClick = {
                        entry.value = null
                        key.value = null
                    }) {
                        Icon(Icons.AutoMirrored.Outlined.Undo, contentDescription = null, tint = Color.Red)

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
                    minLines = 3,
                    value = content.value,
                    onValueChange = { content.value = it },
                    placeholder = { Text("Write your thoughts here...") })

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
                    Icon(Icons.Outlined.Image, contentDescription = null)
                }
                    RecordingDialog()

                }

                IconButton(onClick = {
                    saveEntry(
                        Entry(
                            date = date.value,
                            title = content.value,
                            geo = location.value,
                            imageUrl = imageList.value,
                            audio = audioList.value
                        ), key.value
                    )
                }) {
                    Icon(Icons.Outlined.Save, contentDescription = null)
                }
            }


        }
    }
}

