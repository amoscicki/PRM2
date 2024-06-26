package com.example.prm2.ui.screens.entrylist

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.prm2.R
import com.example.prm2.model.Entry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalEntries
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempImageFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempImageFile

@Composable
fun EntryListScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val selectedKey = mutableStateOf<String?>(null)
    val selectedEntry = mutableStateOf<Entry?>(null)

    val dialogVisible = remember {
        mutableStateOf(false)
    }

    val tempImageFile = LocalTempImageFile.current
    val setTempImageFile = LocalSetTempImageFile.current
    val tempAudioFile = LocalTempAudioFile.current
    val setTempAudioFile = LocalSetTempAudioFile.current

    val entries = LocalEntries.current

    Scaffold(modifier = modifier
        .fillMaxSize(),
        topBar = { Search() },
        bottomBar = {
            QuickEditEntryCard(
                selectedEntry,
                selectedKey
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp)
            ) {
                items(entries.keys.toList().sortedBy {
                    entries[it]?.date?.time ?: 0

                }) { key ->
                    EntryCard(entries[key]!!) {
                        if (selectedKey.value == null && (tempAudioFile != null || tempImageFile != null)) {
                            dialogVisible.value = true
                            return@EntryCard
                        }
                        selectedEntry.value = entries[key]
                        selectedKey.value = key
                    }
                }
            }

            if (dialogVisible.value)
                Dialog(onDismissRequest = { dialogVisible.value = false }) {
                    Surface {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = spacedBy(8.dp)
                        ) {

                            Text(text = stringResource(R.string.recording_or_picture_is_already_set_do_you_want_to_discard))
                            Spacer(modifier = Modifier.fillMaxWidth())
                            Row(modifier = Modifier, horizontalArrangement = spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        setTempAudioFile(null)
                                        setTempImageFile(null)
                                        dialogVisible.value = false
                                    }
                                ) {
                                    Text(text = stringResource(R.string.yes))
                                }
                                Button(onClick = { dialogVisible.value = false }) {
                                    Text(text = stringResource(R.string.cancel))
                                }
                            }
                        }
                    }
                }
        }
    }
}

