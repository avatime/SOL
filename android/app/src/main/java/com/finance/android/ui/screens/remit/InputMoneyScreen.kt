package com.finance.android.ui.screens


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
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.ButtonType

import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.RemitViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputMoneyScreen(modifier: Modifier, remitViewModel: RemitViewModel) {

    var moneyValue by remember {
        mutableStateOf("")
    }

    var balance by remember {
        mutableStateOf("55555")
    }


    var error = remember { mutableStateOf(false) }

    var placeholderText = remember {
        mutableStateOf("얼마를 보낼까요?")
    }

    var keyboardController = LocalSoftwareKeyboardController.current

    //보내는 계좌 은행
    var acName by remember {
        mutableStateOf("국민은행")
    }

    //받는 계좌 은행
    var acTag by remember {
        mutableStateOf("국민은행")
    }

    //보내는 계좌 번호
    var acSend by remember {
        mutableStateOf("015402040675")

    }

    //받는 계좌 번호
    var acReceive by remember {
        mutableStateOf("010-4901-6695")
    }

    //나에게 표시
    var receive by remember {
        mutableStateOf("")
    }

    //받는 분에게 표시
    var send by remember {
        mutableStateOf("")
    }

    var isNext by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()



    if (moneyValue.isNotEmpty() && (Integer.parseInt(moneyValue) > Integer.parseInt(balance))) {
        error.value = true
    }

    if (moneyValue.isNotEmpty() && (Integer.parseInt(moneyValue) <= Integer.parseInt(balance))) {
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

        Column() {

            if (isNext) {
                TextButton(
                    onClick = { isNext = false },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 20.dp)


                ) {
                    Text(text = "${moneyValue}원 보낼까요?", fontSize = 40.sp)
                }


            } else {
                TextField(
                    value = moneyValue, onValueChange = { it -> moneyValue = it },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
//                .height(150.dp)
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = placeholderText.value,
                            fontSize = 40.sp,
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
                    textStyle = androidx.compose.ui.text.TextStyle().copy(fontSize = 50.sp),
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



            if (moneyValue.isNotEmpty() && (Integer.parseInt(moneyValue) > Integer.parseInt(balance))) {
                TextButton(
                    onClick = { moneyValue = balance },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 30.dp)


                ) {
                    Text(text = "잔액 ${balance}원 입력", fontSize = 20.sp)
                }

            }

            if (moneyValue.isEmpty()) {
                TextButton(
                    onClick = { moneyValue = balance },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
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
                            remitViewModel.remitFromAccount(
                                value = Integer.parseInt(moneyValue),
                                receive = "",
                                send = "",
                                onSuccess = {
//                                    navController.navigate()
                                }
                            )
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



