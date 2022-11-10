package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.UserBalanceInfo
import com.finance.android.ui.components.showHistoryList
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.BankViewModel
import java.text.DecimalFormat

@Composable
fun AccountDetailFragment(
    navController: NavController,
    onClose: () -> Unit,
    acName: String,
    cpName: String,
    acNo: String,
    bankViewModel: BankViewModel = hiltViewModel(),
) {
    fun launch() {
        bankViewModel.loadAccountBalance(acNo = acNo)
        bankViewModel.loadAccountHistory(acNo = acNo)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Scaffold(
        topBar = {
            BackHeaderBar(text = "내 계좌", modifier = Modifier, onClickBack = onClose)
        },

        ) { innerPaddingModifier ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            when (val data = bankViewModel.getLoadAccountBalanceandHistory()) {
                is Response.Success -> {
                    val balance = (bankViewModel.accountBalance.value as Response.Success).data
                    val accountHistoryList = (bankViewModel.accountHistory.value as Response.Success).data
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                top = innerPaddingModifier.calculateTopPadding(),
                                start = dimensionResource(R.dimen.padding_small),
                                end = dimensionResource(R.dimen.padding_small)
                            )
                            .verticalScroll(rememberScrollState())
                    ) {
                        UserBalanceInfo(
                            title = acName,
                            cpName = cpName,
                            acNo = acNo,
                            balance = DecimalFormat("#,###원").format(balance) ?: "0원",
                            onClick = {
                                navController.navigate("${Const.Routes.REMIT}/${cpName}/${acNo}/${balance}")
                            }
                        )
                        showHistoryList(modifier = Modifier.weight(1.0f),
                            historyList = List(accountHistoryList.size) {i -> accountHistoryList[i].toEntity()})
                    }
                }
                is Response.Loading -> {}
                else -> {}
            }
        }

    }

}