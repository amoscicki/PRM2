package com.example.prm2.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String?,
    highlight: Boolean = false,
    defaultPadding: Boolean = true,
    DialogContent: @Composable () -> Unit
) {

    val showDialog = remember { mutableStateOf(false) }


    fun showDialog() {
        showDialog.value = true
    }



    IconButton(onClick = {
        showDialog()
    }) {
        Icon(
            icon,
            contentDescription,
            tint = if (highlight) Color.hsl(30f, .7f, .4f) else LocalContentColor.current
        )
    }

    if (showDialog.value)
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = defaultPadding)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DialogContent()
                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = { showDialog.value = false })
                    {
                        Text("Dismiss")
                    }
                }
            }
        }

}