package com.finance.android

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.fragments.*
import com.finance.android.ui.screens.AttendanceFragment
import com.finance.android.ui.theme.FinanceTheme
import com.finance.android.utils.Const
import dagger.hilt.android.HiltAndroidApp

@OptIn(ExperimentalAnimationApi::class)
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
                MainFragment(navController = navController)
            }
            composable(Const.Routes.REMIT) {
                RemitFragment()
            }
            composable(Const.Routes.ADD_ASSET) {
                AnimatedVisibility(
                    initiallyVisible = false,
                    visible = true,
                    enter = slideInVertically(
                        initialOffsetY = { it / 2 }
                    ),
                    exit = slideOutVertically()
                ) {
                    AddAssetFragment(
                        onClose = {
                            navController.popBackStack()
                        }
                    )
                }
            }
            composable(Const.Routes.ATTENDANCE) {
                AnimatedVisibility(
                    initiallyVisible = false,
                    visible = true,
                    enter = slideInVertically(
                        initialOffsetY = { it / 2 }
                    ),
                    exit = slideOutVertically()
                ) {
                    AttendanceFragment()
                }
            }


        }
    }
}

@HiltAndroidApp
class FinanceApplication : Application()
