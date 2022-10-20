package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.BottomNavBar
import com.finance.android.utils.Const

@Composable
fun MainFragment() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                modifier = Modifier
            )
        }
    ) { innerPaddingModifier ->
        NavHost(
            navController = navController,
            startDestination = Const.HOME_SCREEN,
            modifier = Modifier.padding(innerPaddingModifier)
        ) {
            composable(Const.HOME_SCREEN) {
                HomeScreen()
            }
            composable(Const.PRODUCT_SCREEN) {
                ProductScreen()
            }
            composable(Const.STOCK_SCREEN) {
                StockScreen()
            }
            composable(Const.MORE_SCREEN) {
                MoreScreen()
            }
        }
    }
}
