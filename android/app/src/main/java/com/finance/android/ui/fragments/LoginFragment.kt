package com.finance.android.ui.fragments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.BuildConfig
import com.finance.android.datastore.UserStore
import com.finance.android.ui.screens.login.InputPasswordScreen
import com.finance.android.ui.screens.login.InputPasswordType
import com.finance.android.ui.screens.login.SplashScreen
import com.finance.android.ui.theme.SetStatusBarColor
import com.finance.android.utils.Const
import com.finance.android.viewmodels.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginFragment(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val refreshToken = UserStore(LocalContext.current).getValue(UserStore.KEY_REFRESH_TOKEN)
    val password = UserStore(LocalContext.current).getValue(UserStore.KEY_PASSWORD)
    var showInputPassword by remember { mutableStateOf(false) }

    SetStatusBarColor(color = MaterialTheme.colorScheme.surface)
    LaunchedEffect(Unit) {
        if (!BuildConfig.DEBUG) {
            delay(3700)
        }
        refreshToken.collect {
            if (it.isEmpty()) {
                navController.navigate(Const.Routes.SIGNUP) {
                    popUpTo(Const.Routes.LOGIN) {
                        inclusive = true
                    }
                }
                return@collect
            }

            // 개발용 자동로그인
            if (BuildConfig.DEBUG) {
                password.collect { pass ->
                    if (pass.isEmpty()) {
                        return@collect
                    }
                    loginViewModel.password.value = pass
                    loginViewModel.login(
                        onSuccess = {
                            navController.navigate(Const.Routes.MAIN) {
                                popUpTo(Const.Routes.LOGIN) {
                                    inclusive = true
                                }
                            }
                        },
                        onErrorPassword = {}
                    )
                }
            }

            showInputPassword = true
        }
    }

    AnimatedVisibility(
        visible = !showInputPassword,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        SplashScreen()
    }
    AnimatedVisibility(
        visible = showInputPassword,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        InputPasswordScreen(
            inputPasswordType = InputPasswordType.LOGIN,
            loginViewModel = loginViewModel,
            onNextStep = {
                navController.navigate(Const.Routes.MAIN) {
                    popUpTo(Const.Routes.LOGIN) {
                        inclusive = true
                    }
                }
            },
            isLoginFragment = true
        )
    }
}
