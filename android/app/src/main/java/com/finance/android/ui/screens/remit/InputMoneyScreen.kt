package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.utils.Const
import com.finance.android.utils.NumberCommaTransformation
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.RemitViewModel
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputMoneyScreen(
    modifier: Modifier,
    remitViewModel: RemitViewModel,
    innerNavController: NavController,
    outerNavController: NavController
) {
    var moneyValue by remember {
        remitViewModel.moneyValue
    }

    var balance by remember {
        mutableStateOf(remitViewModel.balance)
    }

    var error = remember { mutableStateOf(false) }

    var placeholderText = remember {
        mutableStateOf("얼마를 보낼까요?")
    }

    var keyboardController = LocalSoftwareKeyboardController.current

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

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box {
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
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.padding(start = 20.dp)

                    ) {
                        Column() {
                            Text(
                                text = DecimalFormat("#,###원").format(moneyValue.toInt()) + "을",
                                fontSize = 30.sp,
                            )
                            Text(
                                text = "보낼까요?",
                                fontSize = 30.sp,
                            )

                        }

                    }
                } else {
                    TextField(
                        value = moneyValue,
                        onValueChange = {
                            if (!Pattern.matches("^[0-9]*$", it)) return@TextField
                            if (it.isNotEmpty() && it.toLong() > Int.MAX_VALUE) return@TextField
                            if (error.value && moneyValue < it) {
                                return@TextField
                            } else if (moneyValue == it) return@TextField

                            moneyValue = if (it.isEmpty()) "" else it.toInt().toString()
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = placeholderText.value,
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center
                            )
                        },
                        colors = TextFieldDefaults
                            .textFieldColors(
                                backgroundColor = Transparent,
                                focusedIndicatorColor = Transparent,
                                unfocusedIndicatorColor = Transparent,
                                cursorColor = Transparent
                            ),
                        textStyle = androidx.compose.ui.text.TextStyle().copy(fontSize = 40.sp),
                        isError = error.value,
                        visualTransformation = if (moneyValue.isNotEmpty()) NumberCommaTransformation() else VisualTransformation.None
                    )

                    if (error.value) {
                        Text(
                            text = "잔액을 초과한 금액은 입력할 수 없습니다.",
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
                            backgroundColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.padding(start = 30.dp)

                    ) {
                        Text(
                            text = "잔액 " + DecimalFormat("#,###원").format(balance) + "(클릭시 입력)",
                            fontSize = 20.sp
                        )
                    }
                }

                if (moneyValue.isEmpty()) {
                    TextButton(
                        onClick = { moneyValue = balance.toString() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.padding(start = 30.dp)

                    ) {
                        Text(
                            text = "잔액 " + DecimalFormat("#,###원").format(balance) + "(클릭시 입력)",
                            fontSize = 20.sp
                        )
                    }
                }

                if (moneyValue.isNotEmpty() && 0 < moneyValue.toInt()) {
                    if (!isNext) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        com.finance.android.ui.components.TextButton(
                            onClick = { isNext = true },
                            text = "다음",
                            modifier = Modifier.withBottomButton(),
                            buttonType = ButtonType.ROUNDED,
                            enabled = !error.value
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1.0f))

                        com.finance.android.ui.components.TextButton(
                            onClick = {
                                outerNavController.navigate(Const.Routes.INPUT_PASSWORD)
                            },
                            text = "보내기",
                            modifier = Modifier.withBottomButton(),
                            buttonType = ButtonType.ROUNDED

                        )
                    }
                }
            }
        }


        outerNavController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("input_password")
            ?.observeAsState()
            ?.value?.let {
                if (it) {
                    AnimatedLoading(
                        modifier = Modifier.background(androidx.compose.material3.MaterialTheme.colorScheme.surface),
                        text = "송금하고 있어요"
                    )

                    LaunchedEffect(it) {
                        if (!remitViewModel.requestRemit.value && Integer.parseInt(moneyValue) > 0) {
                            remitViewModel.remitFromAccount(
                                value = Integer.parseInt(moneyValue),
                                receive = "",
                                send = "",
                                onSuccess = {
                                    innerNavController.navigate("${Const.REMIT_OK_SCREEN}/$moneyValue/송금 완료")
                                }
                            )
                        } else if (Integer.parseInt(moneyValue) > 0) {
                            remitViewModel.remitFromPhone(
                                value = Integer.parseInt(moneyValue),
                                receive = "",
                                send = "",
                                onSuccess = {
                                    innerNavController.navigate("${Const.REMIT_OK_SCREEN}/$moneyValue/$it")
                                }
                            )
                        }
                   }
                }
        }
    }
}
