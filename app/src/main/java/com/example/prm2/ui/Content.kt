package com.example.prm2.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prm2.ui.theme.PRM2Theme

@Composable
fun Content() {
    PRM2Theme {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
            topBar = { TopMenu() },
            bottomBar = { AddNewEntry() }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Search()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp)
                ) {
                    items(10) {
                        Entry()
                    }
                }
            }
        }
    }
}

