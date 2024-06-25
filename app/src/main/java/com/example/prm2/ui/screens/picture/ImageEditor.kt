package com.example.prm2.ui.screens.picture

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.KeyEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ImageEditor(bitmap: Bitmap) {
    val focusManager = LocalFocusManager.current
    val editedBitmap = remember { mutableStateOf(bitmap) }
    val text = remember { mutableStateOf("") }
    val textPosition = remember { mutableStateOf(Offset.Zero) }
    val textBoxVisible = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val fontStyle = TextStyle(color = Color.Red, fontSize = LocalDensity.current.run { 50f.toSp() }
    )
    val textViewColors = colors(
        focusedContainerColor = Color.hsl(0f, 0f, 0f,.2f),
        unfocusedContainerColor = Color.Transparent,
        disabledTextColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    @Composable
    fun onDoneHandler(): KeyboardActionScope.() -> Unit = {
        editedBitmap.value =
            ImageCanvas.addTextToBitmap(
                editedBitmap.value,
                text.value,
                textPosition.value
            )
        text.value = ""
        textBoxVisible.value = false

        focusManager.clearFocus()
        defaultKeyboardAction(ImeAction.Done)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
            TextField(
            shape = RoundedCornerShape(40.dp),
            value = text.value,
            onValueChange = { text.value = it },
            textStyle = fontStyle,
            colors = textViewColors,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = onDoneHandler()
            ),
            singleLine = true,
            modifier = Modifier
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        textBoxVisible.value = false
                        return@onKeyEvent true
                    }
                    false
                }
                .focusRequester(focusRequester)
                .absoluteOffset(
                    x = textPosition.xAsDp(),
                    y = textPosition.yAsDp()
                )
                .zIndex(1f)
        )
        Canvas(
            modifier = Modifier
                .weight(1f)
                .border(4.dp, Color.Gray)
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

@Composable
fun MutableState<Offset>.xAsDp() = LocalDensity.current.run {
    value.x.toInt().toDp() - 15.dp
}

@Composable
fun MutableState<Offset>.yAsDp() = LocalDensity.current.run {
    value.y.toInt().toDp() + 25.dp
}