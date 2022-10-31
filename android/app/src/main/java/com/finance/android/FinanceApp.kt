package com.finance.android

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.fragments.LoginFragment
import com.finance.android.ui.fragments.MainFragment
import com.finance.android.ui.fragments.RemitFragment
import com.finance.android.ui.fragments.SignupFragment
import com.finance.android.ui.theme.FinanceTheme
import com.finance.android.utils.Const
import dagger.hilt.android.HiltAndroidApp

@Composable
fun FinanceApp() {
    FinanceTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Const.Routes.LOGIN
        ) {
            composable(Const.Routes.LOGIN) {
                LoginFragment(navController = navController)
            }
            composable(Const.Routes.SIGNUP) {
                SignupFragment(navController = navController)
            }
            composable(Const.Routes.MAIN) {
                MainFragment()
            }
            composable(Const.Routes.REMIT) {
                RemitFragment()
            }
        }
    }
}

@HiltAndroidApp
class FinanceApplication: Application()