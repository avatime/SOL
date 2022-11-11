package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.CardDetailComp
import com.finance.android.ui.components.UserBalanceInfo
import com.finance.android.ui.components.showHistoryList
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.CardViewModel
import java.text.DecimalFormat

@Composable
fun CardDetailFragment(
    navController: NavController,
    onClose: () -> Unit,
    cdName: String,
    cdNo: String,
    cdImgPath: String,
    balance: Int,
    cardViewModel: CardViewModel = hiltViewModel(),
) {
    fun launch() {
        cardViewModel.loadCardHistory(cdNo)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Scaffold(
        topBar = {
            BackHeaderBar(text = "내 카드", modifier = Modifier, onClickBack = onClose)
        },

        ) { innerPaddingModifier ->
        Column(modifier = Modifier
            .padding(top = innerPaddingModifier.calculateTopPadding())
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            when (val data = cardViewModel.getLoadCardHistory()) {
                is Response.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = dimensionResource(R.dimen.padding_small),
                                start = dimensionResource (R.dimen.padding_small),
                                end = dimensionResource(R.dimen.padding_small)
                            )
                            .verticalScroll(rememberScrollState())
                    ) {
                        CardDetailComp(
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface),
                            cdName = cdName,
                            cdImgPath = cdImgPath,
                            cdNo = cdNo,
                            balance = balance
                        )
//                        showHistoryList(modifier = Modifier.weight(1.0f),
//                            historyList = List(accountHistoryList.size) {i -> accountHistoryList[i].toEntity()})
                    }
                }
                is Response.Loading -> {
                    Text(text = "로딩중")
                }
                else -> {
                    Text(text = "실패")
                }
            }
        }

    }

}