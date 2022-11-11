package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.ui.components.HeaderProductTabBar
import com.finance.android.utils.Response
import com.finance.android.viewmodels.CardViewModel

@Composable
fun ProductScreen(
    navController: NavController,
    cardViewModel: CardViewModel = hiltViewModel()
) {
    fun launch() {
        cardViewModel.loadCardRecommend()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    when (val data = cardViewModel.getLoadCardReccommend()) {
        is Response.Success -> {
            val cardRecommendList = (cardViewModel.cardRecommendList.value as Response.Success).data
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                HeaderProductTabBar(
                    modifier = Modifier,
                    navController = navController,
                    cardRecommendList = cardRecommendList
                )
            }
        }
        is Response.Loading -> {}
        else -> {}
    }
}
