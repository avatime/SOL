package com.finance.android.ui.screens.remit


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.ui.components.ButtonType
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.RemitViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputMoneyScreen(
    modifier: Modifier,
    remitViewModel: RemitViewModel,
    navController: NavController
) {

    var moneyValue by remember {
        mutableStateOf("")
    }

    val balance by remember {
        mutableStateOf(remitViewModel.balance)
    }


    val error = remember { mutableStateOf(false) }

    val placeholderText = remember {
        mutableStateOf("얼마를 보낼까요?")
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    var isNext by remember {
        mutableStateOf(false)
    }

    if (moneyValue.isNotEmpty() && (Integer.parseInt(moneyValue) > balance!!)) {
        error.value = true
    }

    if (moneyValue.isNotEmpty() && (Integer.parseInt(moneyValue) <= balance!!)) {
        error.value = false
    }

    if (moneyValue == "0" || moneyValue == "") {
        placeholderText.value = "얼마를 보낼까요?"
    }

    if (isNext) {
        keyboardController?.hide()
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            if (isNext) {
                TextButton(
                    onClick = { isNext = false },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Transparent,
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 20.dp)


                ) {
                    Text(
                        text = "${moneyValue}원 보낼까요?",
                        fontSize = 30.sp,
                        softWrap = true,
                        maxLines = 1
                    )
                }


            } else {
                TextField(
                    value = moneyValue,
                    onValueChange = {

                        if (error.value && moneyValue < it) {
                            return@TextField
                        }

                        moneyValue = if (it.isEmpty()) "" else it.toInt().toString()
                    },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = placeholderText.value,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                        )

                    },
                    colors = TextFieldDefaults
                        .textFieldColors(
                            backgroundColor = Transparent,
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            cursorColor = Transparent,
                        ),
                    textStyle = androidx.compose.ui.text.TextStyle().copy(fontSize = 40.sp),
                    isError = error.value,


                    )

                if (error.value) {
                    Text(
                        text = "잔액이 ${balance}원이에요.",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                }

            }



            if (moneyValue.isNotEmpty() && (Integer.parseInt(moneyValue) > balance!!)) {
                TextButton(
                    onClick = { moneyValue = balance.toString() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Transparent,
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 30.dp)


                ) {
                    Text(text = "잔액 ${balance}원 입력", fontSize = 20.sp)
                }

            }

            if (moneyValue.isEmpty()) {
                TextButton(
                    onClick = { moneyValue = balance.toString() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Transparent,
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 30.dp)


                ) {
                    Text(text = "잔액 ${balance}원 입력", fontSize = 20.sp)
                }

            }

            if (moneyValue.isNotEmpty()) {

                if (!isNext) {
                    Spacer(modifier = Modifier.weight(1.0f))

                    com.finance.android.ui.components.TextButton(
                        onClick = { isNext = true },
                        text = "다음",
                        modifier = Modifier.withBottomButton(),
                        buttonType = ButtonType.ROUNDED,

                        )

                } else {
                    Spacer(modifier = Modifier.weight(1.0f))

                    com.finance.android.ui.components.TextButton(
                        onClick = {
                            if (!remitViewModel.requestRemit.value && Integer.parseInt(moneyValue) > 0) {
                                remitViewModel.remitFromAccount(
                                    value = Integer.parseInt(moneyValue),
                                    receive = "",
                                    send = "",
                                    onSuccess = {
                                        navController.navigate(Const.REMIT_OK_SCREEN)
                                        remitViewModel.moneyValue.value = moneyValue
                                    }
                                )
                            } else if (Integer.parseInt(moneyValue) > 0) {
                                remitViewModel.remitFromPhone(value = Integer.parseInt(moneyValue),
                                    receive = "",
                                    send = "",
                                    onSuccess = {
                                        navController.navigate(Const.REMIT_OK_SCREEN)
                                        remitViewModel.moneyValue.value = moneyValue
                                    })
                            }

                        },
                        text = "보내기",
                        modifier = Modifier.withBottomButton(),
                        buttonType = ButtonType.ROUNDED,


                        )

                }


            }


        }


    }


}



