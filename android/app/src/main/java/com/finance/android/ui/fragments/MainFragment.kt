package com.finance.android.ui.fragments

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.BottomNavBar
import com.finance.android.ui.components.MainBackPressHandler
import com.finance.android.ui.screens.HomeScreen
import com.finance.android.ui.screens.MoreScreen
import com.finance.android.ui.screens.ProductScreen
import com.finance.android.ui.screens.StockScreen
import com.finance.android.utils.Const

@ExperimentalAnimationApi
@Composable
fun MainFragment(navController: NavController) {
    val innerNavController = rememberNavController()
    MainBackPressHandler()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = innerNavController,
                modifier = Modifier
            )
        }
    ) { innerPaddingModifier ->
        NavHost(
            navController = innerNavController,
            startDestination = Const.HOME_SCREEN,
            modifier = Modifier.padding(innerPaddingModifier)
        ) {
            composable(Const.HOME_SCREEN) {
                HomeScreen(navController = navController)
            }
            composable(Const.PRODUCT_SCREEN) {
                ProductScreen(navController = navController)
            }
            composable(Const.STOCK_SCREEN) {
                StockScreen(navController = navController)
            }
            composable(Const.MORE_SCREEN) {
                MoreScreen(navController = navController)
            }
        }
    }
}
