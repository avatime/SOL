package com.finance.android.ui.screens.remit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.ui.components.AccountLikeItem
import com.finance.android.utils.Response
import com.finance.android.viewmodels.RemitViewModel

@Composable
fun RecoScreen(
    remitViewModel: RemitViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    fun launch() {
        remitViewModel.getRecommendedAccountData()
//        bankViewModel.getAllBankAccountData()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Recent(
            recommendedAccountData = remitViewModel.recommendedAccountData,
//            allAccountData = bankViewModel.allBankAccountData
        )

    }

}


@Composable
private fun Recent(
    recommendedAccountData: MutableState<Response<MutableList<RecentTradeResponseDto>>>,
//    allAccountData: MutableState<Response<MutableList<BankInfoResponseDto>>>



) {
    Column() {
        Spacer(modifier = Modifier.padding(8.dp) )
        Text(text = "내 계좌", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.padding(8.dp) )

//        when (val response = allAccountData.value) {
//            is Response.Failure -> Text(text = "실패")
//            is Response.Loading -> Text(text = "로딩중")
//            is Response.Success -> response.data.forEach {
//                AccountLikeItem(
//                    bkStatus = it.,
//                    cpLogo = it.cpLogo,
//                    name = it.acReceive,
//                    accountNumber = it.acNo,
//                    cpName = it.cpName
//                )
//
//            }
//        }

        Spacer(modifier = Modifier.padding(8.dp) )
        Text(text = "최근 보낸 계좌", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.padding(8.dp) )

        when (val response = recommendedAccountData.value) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> Text(text = "로딩중")
            is Response.Success -> response.data.forEach {
                AccountLikeItem(
                    bkStatus = it.bkStatus,
                    cpLogo = it.cpLogo,
                    name = it.acReceive,
                    accountNumber = it.acNo,
                    cpName = it.cpName
                )

            }
        }


    }
}

