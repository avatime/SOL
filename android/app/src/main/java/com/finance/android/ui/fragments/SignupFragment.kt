package com.finance.android.ui.fragments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.ui.screens.login.*
import com.finance.android.utils.Const
import com.finance.android.viewmodels.LoginViewModel

enum class SignupStep(
    val id: Int
) {
    InputUserInfo(0),
    TestPhone(1),
    InputPassword(2),
    Done(3)
}

@Composable
fun SignupFragment(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var step by remember { mutableStateOf(SignupStep.InputUserInfo) }
    val onNextStep = { step = SignupStep.values()[step.id + 1] }
    var inputPasswordType by remember { mutableStateOf(InputPasswordType.LOGIN) }

    AnimatedVisibility(
        visible = step == SignupStep.InputUserInfo,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        InputUserInfoScreen(
            loginViewModel = loginViewModel,
            onNextStep = onNextStep,
            setInputPasswordType = {
                inputPasswordType = it
            }
        )
    }
    AnimatedVisibility(
        visible = step == SignupStep.TestPhone,
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
        visible = step == SignupStep.InputPassword,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        InputPasswordScreen(
            inputPasswordType = inputPasswordType,
            loginViewModel = loginViewModel,
            onNextStep = if (inputPasswordType == InputPasswordType.SIGNUP) onNextStep else {
                {
                    navController.navigate(Const.Routes.MAIN) {
                        popUpTo(Const.Routes.SIGNUP) {
                            inclusive = true
                        }
                    }
                }
            },
            isLoginFragment = false
        )
    }
    AnimatedVisibility(
        visible = step == SignupStep.Done,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        LoginDoneScreen(
            onNextStep = {
                navController.navigate(Const.Routes.MAIN) {
                    popUpTo(Const.Routes.SIGNUP) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
