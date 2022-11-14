package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Text(text = "${DecimalFormat("#,###원").format(groupAccountViewModel.duesVal.value)}을 보내겠습니까?")
        TextButton(
            onClick = {
                if (groupAccountViewModel.screenType.value == 1) {
                    groupAccountViewModel.postPayDues(
                        RemitDuesRequestDto(
                            duesVal = groupAccountViewModel.duesVal.value,
                            duesId = groupAccountViewModel.duesId.value
                        ), onSuccess = { navController.navigate(Const.GROUP_ACCOUNT_MAIN_SCREEN) })
                } else if (groupAccountViewModel.screenType.value == 2) {
                    groupAccountViewModel.postDeposit(GroupDepositRequestDto(
                        paId = groupAccountViewModel.paId.value,
                        value = groupAccountViewModel.duesVal.value,
                    ), onSuccess = { navController.navigate(Const.GROUP_ACCOUNT_MAIN_SCREEN) })
                } else if (groupAccountViewModel.screenType.value == 3) {
                    groupAccountViewModel.postWithdraw(
                        GroupWithdrawDuesRequestDto(
                            paId = groupAccountViewModel.paId.value,
                            value = groupAccountViewModel.duesVal.value
                        ), onSuccess = { navController.navigate(Const.GROUP_ACCOUNT_MAIN_SCREEN) })

                }
            },
            text = "확인",
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton()
        )


    }

}