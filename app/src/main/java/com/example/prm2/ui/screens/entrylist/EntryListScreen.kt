package com.example.prm2.ui.screens.entrylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prm2.model.Entry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalEntries

@Composable
fun EntryListScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val selectedKey = mutableStateOf<String?>(null)
    val selectedEntry = mutableStateOf<Entry?>(null)

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
                        selectedEntry.value = entries[key]
                        selectedKey.value = key
                    }
                }
            }

        }
    }
}

