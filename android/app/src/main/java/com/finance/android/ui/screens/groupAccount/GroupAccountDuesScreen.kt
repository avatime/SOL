package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.DuesItem
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountDuesScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel
) {
    fun launch() {
        Log.i("group", "회비 조회 paId : ${groupAccountViewModel.paId.value}")
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
                    androidx.compose.material.Text(text = "실패")
                }
                is Response.Loading -> AnimatedLoading(text = "")
                is Response.Success -> {
                    response.data.forEach {
                        DuesItem(
                            paid = it.paid,
                            name = it.duesName,
                            dueDate = it.dueDate,
                            totalUser = it.totalUSer,
                            paidUser = it.paidUser,
                            duesVal = it.duesVal,
                            onClick = {
                                navController.navigate(Const.GROUP_ACCOUNT_VERIFY_MONEY_SCREEN)
                                groupAccountViewModel.duesVal.value = it.duesVal
                                groupAccountViewModel.duesId.value = it.duesId
                                groupAccountViewModel.screenType.value = 1
                            }
                        )
                    }
                }
            }
        }// end of scrollColumn
        TextButton(
            onClick = { navController.navigate(Const.DUES_MAKE_NAME_SCREEN)
                      groupAccountViewModel.OKtext.value = "회비를 생성했습니다."
                      groupAccountViewModel.duesVal.value = 0},
            text = "회비 걷기",
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton()
        )
    }


}