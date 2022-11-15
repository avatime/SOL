package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.ui.components.BaseScreen
import com.finance.android.ui.components.HeaderProductTabBar
import com.finance.android.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    navController: NavController,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        productViewModel.loadCardRecommend()
    }
    BaseScreen(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background),
        loading = productViewModel.loading.value,
        error = productViewModel.error.value,
        onError = { productViewModel.loadCardRecommend() },
        calculatedTopPadding = 0.dp
    ) {
        if (productViewModel.cardRecommendList.value != null) {
            HeaderProductTabBar(
                modifier = Modifier,
                navController = navController,
                cardRecommendList = productViewModel.cardRecommendList.value!!
            )
        }
    }
}
