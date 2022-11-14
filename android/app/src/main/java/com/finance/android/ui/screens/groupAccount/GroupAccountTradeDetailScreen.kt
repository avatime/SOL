package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.showHistoryList
import com.finance.android.utils.Response
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountTradeDetailScreen(
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier = Modifier,
) {

    fun launch() {
        groupAccountViewModel.getDuesTradeHistory(groupAccountViewModel.paId.value)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (val response = groupAccountViewModel.duesTradeHistoryData.value) {

            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "조그만 기다려주세요!")
            is Response.Success -> {
                showHistoryList(modifier = Modifier.weight(1.0f),
                    historyList = List(response.data.size) { i -> response.data[i].toEntity() })
            }
        }
    }
}
