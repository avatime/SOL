package com.finance.android.ui.fragments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.ui.components.CustomDialog
import com.finance.android.ui.components.DialogActionType
import com.finance.android.ui.components.DialogType
import com.finance.android.ui.screens.login.*
import com.finance.android.ui.theme.SetStatusBarColor
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
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showDialog = false // 알려드립니다 다이얼로그 끄기~
    }

    if (showDialog) {
        CustomDialog(
            dialogType = DialogType.WARNING,
            dialogActionType = DialogActionType.ONE_BUTTON,
            title = "알려드립니다",
            subTitle = "회원가입 시 필요한 이름, 생년월일, 휴대폰번호를\n임의로 입력하셔도 무방합니다\n본 앱에서 사용되는 사용자 데이터는 모두 가상임을 알려드립니다",
            onPositive = { showDialog = false }
        )
    }

    SetStatusBarColor(color = MaterialTheme.colorScheme.surface)
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
            onClickAddAsset = {
                navController.navigate(Const.Routes.MAIN) {
                    popUpTo(Const.Routes.SIGNUP) {
                        inclusive = true
                    }
                }
                navController.navigate(Const.Routes.ADD_ASSET)
            },
            onClickLater = {
                navController.navigate(Const.Routes.MAIN) {
                    popUpTo(Const.Routes.SIGNUP) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
