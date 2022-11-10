package com.finance.android.ui.screens.point

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.domain.dto.request.PointExchangeRequestDto
import com.finance.android.ui.components.ButtonType
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.PointViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputExchangePoint(
    balance: Int = 1000000,
    pointViewModel: PointViewModel
) {
    var error = remember { mutableStateOf(false) }
    var isNext by remember { mutableStateOf(false) }
    var pointValue by remember { mutableStateOf("") }
    var placeholderText = remember { mutableStateOf("얼마를 교환할까요?") }

    var keyboardController = LocalSoftwareKeyboardController.current

    if (pointValue.isNotEmpty() && (Integer.parseInt(pointValue) > balance)) {
        error.value = true
    }

    if (pointValue.isNotEmpty() && (Integer.parseInt(pointValue) <= balance)) {
        error.value = false
    }

    if (pointValue == "0" || pointValue == "") {
        placeholderText.value = "얼마를 교환할까요?"
    } else {
        placeholderText.value = ""
    }

    if (isNext) {
        keyboardController?.hide()
    }
    if (isNext) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextButton(
                onClick = { isNext = false },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Black,
                ),
                modifier = Modifier.padding(start = 20.dp)

            ) {
                androidx.compose.material.Text(
                    text = "${pointValue}포인트를 교환할까요?",
                    fontSize = 30.sp,
                    softWrap = true,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.weight(1.0f))

            com.finance.android.ui.components.TextButton(
                onClick = { pointViewModel.exchangePointToCash(PointExchangeRequestDto(pointValue.toInt())) },
                text = "보내기",
                modifier = Modifier.withBottomButton(),
                buttonType = ButtonType.ROUNDED,
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = pointValue,
                onValueChange = {

                    if (error.value && pointValue < it) {
                        return@TextField
                    } else if (pointValue == it) return@TextField

                    pointValue = if (it.isEmpty()) "" else it.toInt().toString()
                },
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                placeholder = {
                    androidx.compose.material.Text(
                        text = placeholderText.value,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                    )

                },
                colors = TextFieldDefaults
                    .textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Transparent,
                    ),
                textStyle = TextStyle().copy(fontSize = 40.sp),
                isError = error.value,
            )

            if (error.value) {
                androidx.compose.material.Text(
                    text = "보유한 포인트를 초과한 금액은 입력할 수 없습니다.",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 30.dp)
                )
            } else if (pointValue.isEmpty() || !error.value) {
                TextButton(
                    onClick = { pointValue = balance.toString() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 30.dp),
                    enabled = !error.value

                ) {
                    Text(text = "잔액 ${balance}포인트(클릭시 입력)", fontSize = 20.sp)
                }
            }

            if (pointValue.isNotEmpty()) {
                Spacer(modifier = Modifier.weight(1.0f))

                com.finance.android.ui.components.TextButton(
                    onClick = { isNext = true },
                    text = "다음",
                    modifier = Modifier.withBottomButton(),
                    buttonType = ButtonType.ROUNDED,
                )
            }
        }
    }

}