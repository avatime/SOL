package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.domain.dto.request.GroupDepositRequestDto
import com.finance.android.domain.dto.request.GroupWithdrawDuesRequestDto
import com.finance.android.domain.dto.request.RemitDuesRequestDto
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun GroupAccountVerifyMoneyScreen(
    navController: NavController,
    modifier: Modifier,
    groupAccountViewModel: GroupAccountViewModel,
) {
    LaunchedEffect(Unit) {
        Log.i("group", "duesVal : ${groupAccountViewModel.duesVal.value}")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "${DecimalFormat("#,###원").format(groupAccountViewModel.duesVal.value)}을",
            softWrap = false,
            maxLines = 1,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 25.dp)
        )
        Text(
            text = "보내겠습니까?",
            softWrap = false,
            maxLines = 1,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 25.dp)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = {
                if (groupAccountViewModel.screenType.value == 1) {
                    groupAccountViewModel.OKtext.value = "회비 입금 성공"
                    groupAccountViewModel.postPayDues(
                        RemitDuesRequestDto(
                            duesVal = groupAccountViewModel.duesVal.value,
                            duesId = groupAccountViewModel.duesId.value
                        ), onSuccess = {
                            navController.navigate(Const.GROUP_ACCOUNT_COMPLETED)
                        })
                } else if (groupAccountViewModel.screenType.value == 2) {
                    groupAccountViewModel.OKtext.value = "모두의 통장으로 입금 성공"
                    groupAccountViewModel.postDeposit(GroupDepositRequestDto(
                        paId = groupAccountViewModel.paId.value,
                        value = groupAccountViewModel.duesVal.value,
                    ), onSuccess = {
                        navController.navigate(Const.GROUP_ACCOUNT_COMPLETED)
                    })
                } else if (groupAccountViewModel.screenType.value == 3) {
                    groupAccountViewModel.OKtext.value = "모두의 통장에서 출금 성공"
                    groupAccountViewModel.postWithdraw(
                        GroupWithdrawDuesRequestDto(
                            paId = groupAccountViewModel.paId.value,
                            value = groupAccountViewModel.duesVal.value
                        ), onSuccess = {
                            navController.navigate(Const.GROUP_ACCOUNT_COMPLETED)
                        })

                }
            },
            text = "확인",
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton()
        )


    }

}