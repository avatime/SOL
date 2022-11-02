package com.finance.android.ui.fragments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.datastore.UserStore
import com.finance.android.ui.screens.login.InputPasswordScreen
import com.finance.android.ui.screens.login.InputPasswordType
import com.finance.android.ui.screens.login.SplashScreen
import com.finance.android.utils.Const
import com.finance.android.viewmodels.LoginViewModel

@Composable
fun LoginFragment(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val refreshToken = UserStore(LocalContext.current).getValue(UserStore.KEY_REFRESH_TOKEN)
    val password = UserStore(LocalContext.current).getValue(UserStore.KEY_PASSWORD)
    var showInputPassword by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        // TODO 배포할 때, delay 풀기
//        delay(3700)
        refreshToken.collect {
            if (it.isEmpty()) {
                navController.navigate(Const.Routes.SIGNUP) {
                    popUpTo(Const.Routes.LOGIN) {
                        inclusive = true
                    }
                }
                return@collect
            }

            // TODO 배포할 때, 삭제할 껏 (개발용 자동로그인)
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
            // TODO 개발용 자동로그인 끝

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
