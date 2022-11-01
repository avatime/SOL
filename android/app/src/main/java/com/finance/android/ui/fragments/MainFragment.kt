package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.BottomNavBar
import com.finance.android.ui.screens.HomeScreen
import com.finance.android.ui.screens.MoreScreen
import com.finance.android.ui.screens.ProductScreen
import com.finance.android.ui.screens.StockScreen
import com.finance.android.utils.Const

@Composable
fun MainFragment(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                modifier = Modifier
            )
        }
    ) { innerPaddingModifier ->
        NavHost(
            navController = rememberNavController(),
            startDestination = Const.HOME_SCREEN,
            modifier = Modifier.padding(innerPaddingModifier)
        ) {
            composable(Const.HOME_SCREEN) {
                HomeScreen(navController = navController)
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
            composable(Const.ASSET_FRAGMENT) {
                AssetFragment(navController = navController)
            }
        }
    }
}
