package com.finance.android.ui.fragments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.ui.screens.login.InputPasswordScreen
import com.finance.android.ui.screens.login.InputUserInfoScreen
import com.finance.android.ui.screens.login.LoginDoneScreen
import com.finance.android.ui.screens.login.TestPhoneScreen
import com.finance.android.viewmodels.LoginViewModel

@Composable
fun LoginFragment(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var step by remember { mutableStateOf(LoginStep.InputUserInfo) }
    val onNextStep = { step = LoginStep.values()[step.id + 1] }
    AnimatedVisibility(
        visible = step == LoginStep.InputUserInfo,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        InputUserInfoScreen(
            loginViewModel = loginViewModel,
            onNextStep = onNextStep
        )
    }
    AnimatedVisibility(
        visible = step == LoginStep.TestPhone,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        TestPhoneScreen(
            loginViewModel = loginViewModel,
            onNextStep = onNextStep
        )
    }
    AnimatedVisibility(
        visible = step == LoginStep.InputPassword,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        InputPasswordScreen(
            loginViewModel = loginViewModel,
            onNextStep = onNextStep
        )
    }
    AnimatedVisibility(
        visible = step == LoginStep.Done,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        LoginDoneScreen()
    }
}

enum class LoginStep(
    val id: Int
) {
    InputUserInfo(0),
    TestPhone(1),
    InputPassword(2),
    Done(3)
}
