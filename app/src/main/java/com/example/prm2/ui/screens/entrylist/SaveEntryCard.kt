package com.example.prm2.ui.screens.entrylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prm2.model.Audio
import com.example.prm2.model.Entry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.currentLocation
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.getCountryName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.getLocationName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.saveEntry
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun SaveEntryCard(
    entry: MutableState<Entry?>,
    key: MutableState<String?>,
    ) {
    val saveEntry = saveEntry.current
    val currentLocation = currentLocation.current

    val location = mutableStateOf(entry.value?.geo ?: currentLocation)

    val date = mutableStateOf(entry.value?.date ?: Date())

    val content = mutableStateOf(entry.value?.title ?: "")

    val imageList = mutableStateOf(entry.value?.imageUrls ?: listOf<String>())

    val audioList = mutableStateOf(entry.value?.audioUrls ?: listOf<Audio>())



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
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.EditLocationAlt, contentDescription = null)
                    }
                    Text(text = location.value.let { "${getLocationName.current(it)} ${getCountryName.current(it)}" })
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = date.value.let { SimpleDateFormat("dd/MM/yyyy").format(it) })
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
                    IconButton(onClick = {


                    }) {

                        Icon(Icons.Outlined.Mic, null)
                    }
                    IconButton(modifier = Modifier.padding(8.dp, 0.dp), onClick = {
                        entry.value = null
                        key.value = null
                    }) {
                        Icon(Icons.Outlined.Cancel, contentDescription = null)
                    }
                }

                IconButton(onClick = {
                    saveEntry(
                        Entry(
                            date = date.value,
                            title = content.value,
                            geo = location.value,
                            imageUrls = imageList.value,
                            audioUrls = audioList.value
                        ), key.value
                    )
                }) {
                    Icon(Icons.Outlined.Save, contentDescription = null)
                }
            }


        }
    }
}