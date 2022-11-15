package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import java.lang.Integer.parseInt
import java.util.regex.Pattern


@Composable
fun GroupAccountInputMoneyScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    LaunchedEffect(Unit) {
        groupAccountViewModel.getRepresentAccountBalance() //대표계좌 잔액조회
    }


    val placeholderText = remember {
        mutableStateOf("")
    }
    val duesValue = remember {
        mutableStateOf("")
    }
    if (groupAccountViewModel.duesVal.value > 0) {
        duesValue.value = groupAccountViewModel.duesVal.value.toString()
    }
    //계좌잔액
    val balance = groupAccountViewModel.representAccountBalance.value
    var isValid = remember {
        mutableStateOf(true)
    }
    if (duesValue.value.isEmpty()) {
        placeholderText.value = "얼마를 보낼까요?"
    }
    if (duesValue.value.isEmpty()
        || balance.isEmpty()
        || parseInt(duesValue.value) > parseInt(
            balance
        )
    ) {
        isValid.value = false
    }

    if (duesValue.value == "0") {
        duesValue.value == ""
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TextField(
            value = duesValue.value,
            onValueChange = {
                if(!Pattern.matches("^[0-9]*$", it)) return@TextField
                if(it.isNotEmpty() && it.toLong() > Int.MAX_VALUE) return@TextField
                if (isValid.value && duesValue.value < it) {
                    return@TextField
                }
                else if (duesValue.value == it) return@TextField

               duesValue.value = if (it.isEmpty()) "" else it.toInt().toString()
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
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Transparent,
                ),
            textStyle = TextStyle().copy(fontSize = 40.sp),
            isError = isValid.value,
        )
        if (!isValid.value && balance.isNotEmpty() && parseInt(balance) > 0 && duesValue.value.isNotEmpty() && parseInt(
                balance
            ) < parseInt(duesValue.value)
        ) {
            TextButton(
                onClick = {
                    if (duesValue.value.isNotEmpty() && duesValue.value > "0") {
                        navController.navigate(Const.GROUP_ACCOUNT_VERIFY_MONEY_SCREEN)
                        groupAccountViewModel.duesVal.value = Integer.parseInt(duesValue.value)
                    }

                },
                text = "다음",
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }


    }
}