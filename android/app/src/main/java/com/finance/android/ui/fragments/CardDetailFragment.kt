package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.CardDetailComp
import com.finance.android.ui.components.showHistoryList
import com.finance.android.utils.Response
import com.finance.android.viewmodels.CardViewModel

@Composable
fun CardDetailFragment(
    onClose: () -> Unit,
    cdName: String,
    cdNo: String,
    cdImgPath: String,
    balance: Int,
    cardViewModel: CardViewModel = hiltViewModel(),
    navController : NavController
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
        }

    ) { innerPaddingModifier ->
        Column(
            modifier = Modifier
                .padding(top = innerPaddingModifier.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val cdSecret = cdNo.substring(0 until 4) + "-****-****-" + cdNo.substring(12 until 16)
            when (cardViewModel.getLoadCardHistory()) {
                is Response.Success -> {
                    val cardHistoryList = (cardViewModel.cardHistory.value as Response.Success).data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = dimensionResource(R.dimen.padding_small),
                                start = dimensionResource(R.dimen.padding_small),
                                end = dimensionResource(R.dimen.padding_small)
                            )
                            .verticalScroll(rememberScrollState())
                    ) {
                        CardDetailComp(
                            modifier = Modifier,
                            cdName = cdName,
                            cdImgPath = cdImgPath,
                            cdNo = cdSecret,
                            balance = balance,
                            navController = navController
                        )
                        showHistoryList(
                            modifier = Modifier.weight(1.0f),
                            historyList = List(cardHistoryList.size) { i -> cardHistoryList[i].toEntity() },
                            emptyMessage = "사용 내역이 없어요.",
                            type = "카드"
                        )
                    }
                }
                is Response.Loading -> {
                    AnimatedLoading()
                }
                else -> {
                    Text(text = "실패")
                }
            }
        }
    }
}
