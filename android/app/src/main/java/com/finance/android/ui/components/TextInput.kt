package com.finance.android.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.theme.Disabled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    focus: Boolean = true,
    modifier: Modifier,
    label: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorMessage: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    ) {
    val focusRequester = remember { FocusRequester() }
    var key by remember { mutableStateOf(0) }

    TextField(
        modifier = modifier.focusRequester(focusRequester = focusRequester),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            textColor = Color.Black,
            focusedLabelColor = Color.Black
        ),
        label = { if (label?.isNotEmpty() == true && value.isNotEmpty()) Text(text = label, color = Disabled) },
        trailingIcon = {
            if (focus) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onValueChange("")
                        },
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "clear"
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        isError = isError,
        supportingText = errorMessage,
        placeholder = placeholder,
        textStyle = textStyle
    )

    LaunchedEffect(key, focus) {
        if (focus) {
            focusRequester.requestFocus()
            key++
        } else {
            focusRequester.freeFocus()
        }
    }
}

@Composable
fun CodeTextInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    length: Int = 6,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        decorationBox = {
            Column {
                Row {
                    repeat(length) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(width = 40.dp, height = 45.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (isError) MaterialTheme.colorScheme.error else Color.DarkGray,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = if (it < value.length) {
                                    if (!isPassword) {
                                        value[it].toString()
                                    } else {
                                        "*"
                                    }
                                } else {
                                    " "
                                },
                                textAlign = TextAlign.Center,
                                color = if (isError) MaterialTheme.colorScheme.error else Color.Unspecified,
                                style = textStyle
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                if (isError) {
                    Spacer(modifier = Modifier.height(10.dp))
                    errorMessage?.invoke()
                }
            }
        }
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
