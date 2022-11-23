package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.domain.dto.request.GroupDepositRequestDto
import com.finance.android.domain.dto.request.GroupWithdrawDuesRequestDto
import com.finance.android.domain.dto.request.RemitDuesRequestDto
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import java.text.DecimalFormat

@Composable
fun GroupAccountVerifyMoneyScreen(
    innerNavController: NavController,
    outerNavController: NavController,
    modifier: Modifier,
    groupAccountViewModel: GroupAccountViewModel
) {
    LaunchedEffect(Unit) {
        Log.i("group", "duesVal : ${groupAccountViewModel.duesVal.value}")
    }

   Box {
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
                    outerNavController.navigate(Const.Routes.INPUT_PASSWORD)
                },
                text = "확인",
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }

        outerNavController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("input_password")
            ?.observeAsState()
            ?.value?.let {
                if (it) {
                    val text = when (groupAccountViewModel.screenType.value) {
                        1 -> "회비를 입금"
                        2 -> "모두의 통장으로 입금"
                        3 -> "모두의 통장에서 출금"
                        else -> throw Exception("??")
                    }

                    AnimatedLoading(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                        text = "${text}하고 있어요"
                    )

                    LaunchedEffect(Unit) {
                        if (groupAccountViewModel.screenType.value == 1) {
                            groupAccountViewModel.OKtext.value = "회비 입금 성공"
                            groupAccountViewModel.postPayDues(
                                RemitDuesRequestDto(
                                    duesVal = groupAccountViewModel.duesVal.value,
                                    duesId = groupAccountViewModel.duesId.value
                                ),
                                onSuccess = {
                                    innerNavController.navigate(Const.GROUP_ACCOUNT_COMPLETED) {
                                        popUpTo(Const.GROUP_ACCOUNT_DETAIL_SCREEN)
                                    }
                                }
                            )
                        } else if (groupAccountViewModel.screenType.value == 2) {
                            groupAccountViewModel.OKtext.value = "모두의 통장으로 입금 성공"
                            groupAccountViewModel.postDeposit(
                                GroupDepositRequestDto(
                                    paId = groupAccountViewModel.paId.value,
                                    value = groupAccountViewModel.duesVal.value
                                ),
                                onSuccess = {
                                    innerNavController.navigate(Const.GROUP_ACCOUNT_COMPLETED) {
                                        popUpTo(Const.GROUP_ACCOUNT_DETAIL_SCREEN)
                                    }
                                }
                            )
                        } else if (groupAccountViewModel.screenType.value == 3) {
                            groupAccountViewModel.OKtext.value = "모두의 통장에서 출금 성공"
                            groupAccountViewModel.postWithdraw(
                                GroupWithdrawDuesRequestDto(
                                    paId = groupAccountViewModel.paId.value,
                                    value = groupAccountViewModel.duesVal.value
                                ),
                                onSuccess = {
                                    innerNavController.navigate(Const.GROUP_ACCOUNT_COMPLETED) {
                                        popUpTo(Const.GROUP_ACCOUNT_DETAIL_SCREEN)
                                    }
                                }
                            )
                        }
                        outerNavController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.getLiveData<Boolean>("input_password")
                            ?.value = false
                    }
                }
            }
    }
}
