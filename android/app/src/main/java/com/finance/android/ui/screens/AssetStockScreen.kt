package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.ui.components.AccountListItem
import com.finance.android.ui.components.AccountListItem_Arrow
import com.finance.android.utils.Response
import com.finance.android.viewmodels.FinanceAssetViewModel
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
                    .background(color = Color.White, shape = RoundedCornerShape(10)),
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
        financeData!!.forEach {
            AccountListItem_Arrow(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                onClickItem = {}
            )
        }
        if(financeData.size == 0) {
            Text(text = "등록된 자산이 없어요.", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}