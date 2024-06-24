package com.example.prm2.ui.screens.pin

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.prm2.ui.EntryListRoute

@Composable
fun PinScreen(modifier: Modifier, navController: NavController) {
    val pin = remember { mutableStateOf("") }
    val showError = remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text("Enter your pin")
        TextField(
            value = pin.value,
            onValueChange = {
                pin.value = it
            },
            placeholder = { Text("Enter pin...") }
        )
        if(showError.value)
            Text("Incorrect pin", style =  MaterialTheme.typography.bodyLarge , color =  MaterialTheme.colorScheme.error)
    }
    LaunchedEffect(pin.value) {
        if(pin.value == "1234") {
            navController.navigate(EntryListRoute)
        }
        showError.value = pin.value.length > 0
    }


}