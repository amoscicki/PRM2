package com.example.prm2.ui.screens.picture

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.view.KeyEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import com.example.prm2.extensions.saveBitmapToFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempImageFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempImageFile

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ImageEditor(modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val imageFile = LocalTempImageFile.current
    val updateBitmap = LocalSetTempImageFile.current
    val bitmap = imageFile?.let { BitmapFactory.decodeStream(
        context.contentResolver.openInputStream(it.toUri())
    ) } ?: return
    val focusManager = LocalFocusManager.current
    val editedBitmap = remember { mutableStateOf(bitmap) }
    val text = remember { mutableStateOf("") }
    val textPosition = remember { mutableStateOf(Offset.Zero) }
    val textBoxVisible = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val width = LocalDensity.current.run {
        bitmap.width.toFloat().toDp()
    }
    val height = LocalDensity.current.run {
        bitmap.height.toFloat().toDp()
    }

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
            addTextToBitmap(
                editedBitmap.value,
                text.value,
                textPosition.value
            )
        updateBitmap(editedBitmap.value.saveBitmapToFile(context))

        text.value = ""
        textBoxVisible.value = false

        focusManager.clearFocus(true)
        defaultKeyboardAction(ImeAction.Done)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
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
                            .offset(
                                x = textPosition.xAsDp(),
                                y = textPosition.yAsDp()
                            )
                            .zIndex(1f)
                    )

                Canvas(
                    modifier = Modifier
                        .size(width, height)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
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
                        topLeft = Offset.Zero,
                    )
                }


                }

        }
    }

}

fun addTextToBitmap(bitmap: Bitmap, text: String, position: Offset): Bitmap {
    val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = android.graphics.Canvas(mutableBitmap)
    val paint = Paint().apply {
        color = android.graphics.Color.RED
        textSize = 50f
        isAntiAlias = true
    }
    canvas.drawText(text, position.x, position.y, paint)
    return mutableBitmap
}

@Composable
fun MutableState<Offset>.xAsDp() = LocalDensity.current.run {
    value.x.toInt().toDp()   - 23.dp
}

@Composable
fun MutableState<Offset>.yAsDp() = LocalDensity.current.run {
    value.y.toInt().toDp() - 30.dp
}