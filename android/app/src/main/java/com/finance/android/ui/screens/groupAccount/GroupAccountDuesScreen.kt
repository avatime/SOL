package com.finance.android.ui.screens.groupAccount

import android.icu.util.LocaleData
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anychart.enums.LocaleDateTimeFormat
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.DuesItem
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import java.time.LocalDate
import java.util.Locale

@Composable
fun GroupAccountDuesScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel
) {
    fun launch() {
        Log.i("group", "ํ๋น ์กฐํ paId : ${groupAccountViewModel.paId.value}")
        groupAccountViewModel.postDuesHistory(groupAccountViewModel.paId.value)
    }
    LaunchedEffect(Unit) {
        launch()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            when (val response = groupAccountViewModel.duesHistoryData.value) {
                is Response.Failure -> {
                    androidx.compose.material.Text(text = "์คํจ")
                }
                is Response.Loading -> AnimatedLoading(text = "")
                is Response.Success -> {
                    if (response.data.isEmpty()) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "ํ๋น ๋ด์ญ์ด ์์ด์\nํ๋น ๊ฑท๊ธฐ๋ฅผ ์์ํด๋ณด์ธ์",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    response.data.forEach {
                        //Log.i("group", " ํ๋น data ${it.dueDate}")


                        DuesItem(
                            paid = it.paid,
                            isAvaliableDate = if (it.dueDate.monthValue < LocalDate.now().monthValue) false
                            else if (it.dueDate.monthValue == LocalDate.now().monthValue && it.dueDate.dayOfMonth < LocalDate.now().dayOfMonth) false
                            else {
                                Log.i("group", "dues ๊ฐ์ง ๋?์ง ${it.dueDate}")
                                Log.i(
                                    "group",
                                    "dues ๊ฐ์ง ์ ${it.dueDate.monthValue} ํ์ฌ ์ผ ${it.dueDate.dayOfMonth}"
                                )
                                Log.i(
                                    "group",
                                    "dues ํ์ฌ ์ ${LocalDate.now().monthValue} ํ์ฌ ์ผ ${LocalDate.now().dayOfMonth}"
                                )
                                true
                            },
                            name = it.duesName,
                            dueDate = it.dueDate,
                            totalUser = it.totalUSer,
                            paidUser = it.paidUser,
                            duesVal = it.duesVal,
                            check = it.duesDetailResponseDto.check,
                            onClickPay = {
                                navController.navigate(Const.GROUP_ACCOUNT_VERIFY_MONEY_SCREEN)
                                groupAccountViewModel.duesVal.value = it.duesVal
                                groupAccountViewModel.duesId.value = it.duesId
                                groupAccountViewModel.screenType.value = 1
                            }
                        )
                    }
                }
            }
        } // end of scrollColumn
        TextButton(
            onClick = {
                navController.navigate(Const.DUES_MAKE_NAME_SCREEN)
                groupAccountViewModel.duesVal.value = 0
            },
            text = "ํ๋น ๊ฑท๊ธฐ",
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton()
        )
    }
}
