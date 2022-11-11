package com.finance.android.ui.screens.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.ui.components.AccountListItem_Arrow
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.FinanceViewModel

@Composable
fun AssetStockScreen(
    navController: NavController,
    financeViewModel: FinanceViewModel = hiltViewModel()
) {
    fun launch() {
        financeViewModel.myFinanceLoad()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    when (val data = financeViewModel.getLoadState2()) {
        is Response.Success -> {
            Column()
            {
                AssetStockContainer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    navController = navController,
                    financeData = (financeViewModel.myFinanceList.value as Response.Success).data
                )
            }
        }
        is Response.Loading -> {}
        else -> {}
    }
}

@Composable
fun AssetStockContainer(modifier: Modifier,
                        navController: NavController,
                        financeData: MutableList<BankAccountResponseDto>
) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        financeData.forEach {
            AccountListItem_Arrow(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                acMain = it.acMain,
                onClickItem = {
                    navController.navigate("${Const.Routes.ACC_DETAIL}/${it.acName}/${it.cpName}/${it.acNo}/${it.cpLogo}/${it.acName}")
                }
            )
        }
        if(financeData.size == 0) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "등록된 자산이 없어요.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 80.dp)
                )
            }
        }
    }
}