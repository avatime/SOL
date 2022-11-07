package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.finance.android.ui.components.AccountListItem
import com.finance.android.ui.components.AccountListItem_Arrow
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.BankViewModel
import com.finance.android.viewmodels.HomeViewModel

@Composable
fun AssetBankScreen(
    navController: NavController,
    bankViewModel: BankViewModel = hiltViewModel()
) {
    fun launch() {
        bankViewModel.myAccountLoad()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Column()
    {
        when (val data = bankViewModel.getLoadState()) {
            is Response.Success -> {
                AssetBankContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(10)
                        ),
                    navController = navController,
                    accData = (bankViewModel.accountList.value as Response.Success).data
                )
            }
            is Response.Loading -> {}
            else -> {}
        }
    }
}

@Composable
private fun AssetBankContainer(
    modifier: Modifier,
    navController: NavController,
    accData: MutableList<BankAccountResponseDto>
) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        accData!!.forEach {
            AccountListItem_Arrow(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                onClickItem = {
                    navController.navigate("${Const.Routes.ACC_DETAIL}/${it.acName}/${it.cpName}/${it.acNo}/${it.balance}")
                }
            )
        }
        if(accData.size == 0) {
            Text(text = "등록된 자산이 없어요.", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}