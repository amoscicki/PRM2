package com.example.prm2.ui.screens.entrylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.prm2.R
import com.example.prm2.model.Audio
import com.example.prm2.model.Entry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetCountryName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetLocationName
import java.text.SimpleDateFormat


@Composable
fun EntryCard(entry: Entry, callback: () -> Unit) {

    Card(
        onClick = { callback() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
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
                    Icon(Icons.Outlined.LocationOn, contentDescription = null)
                    Text(text = entry.geo?.let { "${LocalGetLocationName.current(it)} ${LocalGetCountryName.current(it)}" } ?: "")
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    Text(text =
                    entry.date?.let { SimpleDateFormat("dd/MM/yyyy").format(it) }
                        ?: "")
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.DateRange, contentDescription = null)
                    }
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp, 4.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.outlinedCardColors()

                ) {
                    Text(
                        text = stringResource(R.string.title),
                        modifier = Modifier.padding(16.dp, 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        modifier = Modifier.padding(16.dp, 4.dp),
                        text = entry.title ?: stringResource(R.string.no_title)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp, 4.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.outlinedCardColors()

                ) {
                    Text(
                        text = "Note",
                        modifier = Modifier.padding(16.dp, 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )

                    Text(
                        modifier = Modifier.padding(16.dp, 4.dp),
                        text = entry.note ?: stringResource(R.string.empty_note),
                        minLines = 3,
                    )
                }
            }
            Card(
                modifier = Modifier.padding(16.dp, 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(),

                ) {
                Text(
                    text = stringResource(R.string.attachments),
                    modifier = Modifier.padding(16.dp, 4.dp),
                    style = MaterialTheme.typography.labelSmall
                )

                if((entry.audio != null && entry.audio?.url != null && entry.audio?.url != "")
                    || (entry.imageUrl != null && entry.imageUrl != "")) {
                    AudioCard(entry)
                    ImageCard(entry)
                }


            }


        }
    }
}

@Composable
private fun ImageCard(entry: Entry) {
    entry.imageUrl?.let {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = it,
                contentDescription = stringResource(R.string.loaded_image),
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun AudioCard(entry: Entry) {
    entry.audio?.let{ audio ->
        if(audio.url == null || audio.url == "") return
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.PlayCircleOutline, contentDescription = null)
            }
            Text(text = Audio.lengthAsTimeString(audio.lengthSec ?:0))
        }
    }
}