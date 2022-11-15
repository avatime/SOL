package com.finance.android.ui.screens.asset

import android.net.Uri
import androidx.compose.foundation.layout.*
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
import com.finance.android.ui.components.BaseScreen
import com.finance.android.utils.Const
import com.finance.android.viewmodels.BankViewModel

@Composable
fun AssetBankScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    bankViewModel: BankViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        bankViewModel.myAccountLoad()
    }

    BaseScreen(
        loading = bankViewModel.loading.value,
        error = bankViewModel.error.value,
        onError = { bankViewModel.myAccountLoad() },
        calculatedTopPadding = 0.dp
    ) {
        if (bankViewModel.accountList.value != null) {
            AssetBankContainer(
                modifier = modifier,
                navController = navController,
                accData = bankViewModel.accountList.value!!,
            )
        }
    }
}

@Composable
private fun AssetBankContainer(
    modifier: Modifier = Modifier,
    navController: NavController,
    accData: Array<BankAccountResponseDto>,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        accData.forEach {
            val pathTmp = Uri.encode(it.cpLogo)
            AccountListItem_Arrow(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                companyName = it.cpName,
                acMain = it.acMain
            ) {
                navController.navigate("${Const.Routes.ACC_DETAIL}/${it.acName}/${it.cpName}/${it.acNo}/$pathTmp/${it.acMain}/${it.acType}")
            }
        }
        if (accData.isEmpty()) {
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
