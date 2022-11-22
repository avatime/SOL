package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.NumberCommaTransformation
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import java.lang.Integer.parseInt
import java.text.DecimalFormat
import java.util.regex.Pattern


@Composable
fun GroupAccountInputMoneyScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    fun launch() {
        groupAccountViewModel.getRepresentAccountBalance() //대표계좌 잔액조회
        groupAccountViewModel.duesVal.value = 0
        //출금화면일때 balance 모임 통장 잔액으로 바꾸기
        if (groupAccountViewModel.screenType.value == 3) {
            groupAccountViewModel.postGroupAccountInfo(groupAccountViewModel.paId.value)

        }

    }

    LaunchedEffect(Unit) {
        launch()

    }


    var balance = groupAccountViewModel.representAccountBalance.value
    if (groupAccountViewModel.screenType.value == 3) {
        when (val response = groupAccountViewModel.groupAccountInfo.value) {
            is Response.Failure -> {}
            is Response.Loading -> {}
            is Response.Success -> {
                balance = response.data.amount.toString()
            }
        }
    }


    val placeholderText = remember {
        mutableStateOf("")
    }
    val duesValue = remember {
        mutableStateOf("")
    }
    //계좌잔액
    var isError = remember {
        mutableStateOf(false)
    }
    if (duesValue.value.isEmpty()) {
        placeholderText.value = "얼마를 보낼까요?"
    }


    if (duesValue.value == "0") {
        duesValue.value = ""
    }


    if (duesValue.value.isNotEmpty() && (parseInt(duesValue.value) > parseInt(balance)!!)) {
        isError.value = true
    }

    if (duesValue.value.isNotEmpty() && (parseInt(duesValue.value) <= parseInt(balance)!!)) {
        isError.value = false
    }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TextField(
            value = duesValue.value,
            onValueChange = {
                Log.i("group", "인풋: $it,  ${duesValue.value}")
                if (!Pattern.matches("^[0-9]*$", it)) return@TextField
                if (it.isNotEmpty() && it.toLong() > Int.MAX_VALUE) return@TextField
                if (isError.value && duesValue.value < it) {
                    return@TextField
                } else if (duesValue.value == it) return@TextField

                duesValue.value = if (it.isEmpty()) "" else it.toInt().toString()
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
            isError = isError.value,
            visualTransformation = if (duesValue.value.isNotEmpty()) NumberCommaTransformation() else VisualTransformation.None
        )

        if (isError.value) {
            androidx.compose.material.Text(
                text = "잔액 " + DecimalFormat("#,###원").format(parseInt(balance)),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 30.dp)
            )
        }
        AnimatedVisibility(
            visible = !isError.value && duesValue.value.isNotEmpty() && balance.isNotEmpty(),
            enter = slideInVertically(initialOffsetY = { it / 2 }),
            exit = slideOutVertically(targetOffsetY = { 2 * it })
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

