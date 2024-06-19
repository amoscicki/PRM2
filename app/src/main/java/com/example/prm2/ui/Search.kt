package com.example.prm2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Search() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(modifier = Modifier,
                value = "",
                onValueChange = { /* doSomething() */ },
                placeholder = { Text("Search...") })
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Outlined.Search, contentDescription = null)
            }
        }
    }
}