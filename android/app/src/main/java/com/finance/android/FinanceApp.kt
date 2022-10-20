package com.finance.android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.fragments.LoginFragment
import com.finance.android.ui.fragments.MainFragment
import com.finance.android.ui.fragments.SplashFragment
import com.finance.android.ui.theme.FinanceTheme
import com.finance.android.utils.Const

@Composable
fun FinanceApp() {
    FinanceTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Const.Routes.SPLASH
        ) {
            composable(Const.Routes.SPLASH) {
                SplashFragment(navController = navController)
            }
            composable(Const.Routes.LOGIN) {
                LoginFragment()
            }
            composable(Const.Routes.MAIN) {
                MainFragment()
            }
        }
    }
}
