package com.example.prm2.ui.screens.entrylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalKeyword
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetKeyword

@Composable
fun Search() {
    val keyword = mutableStateOf(
        LocalKeyword.current
    )
    val setKeyword = LocalSetKeyword.current


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
                value = keyword.value,
                onValueChange = {
                    setKeyword(it)

                },
                placeholder = { Text("Search...") })
            Icon(Icons.Outlined.Search, contentDescription = null)
        }
    }
}