package com.example.prm2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Mic
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
fun AddNewEntry() {
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
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
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.EditLocationAlt, contentDescription = null)
                    }
                    Text(text = "Warsaw, \nPoland")
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = "01/01/2024")
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
                    value = "",
                    onValueChange = { /* doSomething() */ },
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Mic, contentDescription = null)
                    }
                    IconButton(
                        modifier = Modifier.padding(8.dp, 0.dp),
                        onClick = { /*TODO*/ }) {
                        Icon(Icons.AutoMirrored.Outlined.Backspace, contentDescription = null)
                    }
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.AddCircleOutline, contentDescription = null)
                }
            }


        }
    }
}