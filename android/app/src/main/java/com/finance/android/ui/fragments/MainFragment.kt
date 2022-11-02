package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.BottomNavBar
import com.finance.android.ui.screens.*
import com.finance.android.utils.Const

@Composable
fun MainFragment(navController: NavController) {
    val innerNavController = rememberNavController()
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
                StockScreen()
            }
            composable(Const.MORE_SCREEN) {
//                MoreScreen(navController = navController)
                CalendarScreen()
            }
            composable(Const.ASSET_FRAGMENT) {
                AssetFragment(navController = navController)
            }
        }
    }
}
