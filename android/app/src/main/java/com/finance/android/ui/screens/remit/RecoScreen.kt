package com.finance.android.ui.screens.remit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.domain.dto.response.RecentMyTradeResponseDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.ui.components.AccountLikeItem
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.RemitViewModel

@Composable
fun RecoScreen(
    remitViewModel: RemitViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        remitViewModel.getRecommendedAccountData()
        remitViewModel.getRecentMyAccountData()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Recent(
            recommendedAccountData = remitViewModel.recommendedAccountData,
            recentMyAccountData = remitViewModel.recentMyAccountData,
            remitViewModel = remitViewModel,
            navController = navController
        )

    }

}


@Composable
private fun Recent(
    recommendedAccountData: MutableState<Response<MutableList<RecentTradeResponseDto>>>,
    recentMyAccountData: MutableState<Response<MutableList<RecentMyTradeResponseDto>>>,
    remitViewModel: RemitViewModel,
    navController: NavController
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "내 계좌",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        when (val response = recentMyAccountData.value) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "가져오고 있어요")
            is Response.Success -> {
                response.data.forEach {
                    if(it.acNo != remitViewModel.accountNumber) {
                        AccountLikeItem(
                            bkStatus = it.bkStatus,
                            cpLogo = it.cpLogo,
                            name = it.acName,
                            accountNumber = it.acNo,
                            cpName = it.cpName,
                            onClickBookmark = { remitViewModel.onClickAccountBookmark(it)
                            },
                            onClickItem = {
                                remitViewModel.onClickReceiveBank(
                                    BankInfoResponseDto(cpCode = remitViewModel.cpCode.value,
                                        cpLogo = it.cpName, cpName = it.cpName
                                    )
                                )
                                remitViewModel.validReceiveAccountNumber.value = it.acNo
                                navController.navigate(Const.INPUT_MONEY_SCREEN)
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "최근 보낸 계좌",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        when (val response = recommendedAccountData.value) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "가져오고 있어요")
            is Response.Success -> response.data.forEach {
                AccountLikeItem(
                    bkStatus = it.bkStatus,
                    cpLogo = it.cpLogo,
                    name = it.acReceive,
                    accountNumber = it.acNo,
                    cpName = it.cpName,
                    onClickBookmark = { remitViewModel.onClickAccountBookmark(it)},
                    onClickItem = {
                        remitViewModel.onClickReceiveBank(
                            BankInfoResponseDto(cpCode = remitViewModel.cpCode.value,
                                cpLogo = it.cpName, cpName = it.cpName
                            )
                        )
                        remitViewModel.validReceiveAccountNumber.value = it.acNo
                        navController.navigate(Const.INPUT_MONEY_SCREEN)
                    }
                )
            }
        }
    }
}

