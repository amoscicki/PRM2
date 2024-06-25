package com.example.prm2.ui.screens.picture

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.KeyEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.prm2.dependencyinjection.ImageCanvas

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ImageEditor(bitmap: Bitmap) {
    val focusManager = LocalFocusManager.current
    val editedBitmap = remember { mutableStateOf(bitmap) }
    val text = remember { mutableStateOf("") }
    val textPosition = remember { mutableStateOf(Offset.Zero) }
    val textBoxVisible = remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            textStyle = TextStyle(color = Color.Red, fontSize =
            LocalDensity.current.run {
                50f.toSp()
            }
            ),
            colors = colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    editedBitmap.value =
                        ImageCanvas.addTextToBitmap(
                            editedBitmap.value,
                            text.value,
                            textPosition.value
                        )
                    text.value = ""
                    textBoxVisible.value = false
                    defaultKeyboardAction(ImeAction.Done)
                }
            ),
            singleLine = true,
            modifier = Modifier
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        focusManager.clearFocus()
                        return@onKeyEvent true
                    }
                    false
                }
                .focusRequester(focusRequester)
                .absoluteOffset(
                    x = LocalDensity.current.run {
                        textPosition.value.x
                            .toInt()
                            .toDp()
                    } - 15.dp,
                    y = LocalDensity.current.run {
                        textPosition.value.y
                            .toInt()
                            .toDp()
                    } + 25.dp)
                .zIndex(1f)
        )

        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        textPosition.value = offset
                        textBoxVisible.value = true
                        focusManager.clearFocus(true)
                        focusRequester.requestFocus()
                    }
                }
        ) {
            drawImage(
                image = editedBitmap.value.asImageBitmap(),
                Offset.Zero,

                )
        }

    }

}