package com.finance.android.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.CodeTextInput
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.fragments.SignupStep
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.LoginViewModel

enum class InputPasswordType {
    LOGIN,
    SIGNUP
}

@Composable
fun InputPasswordScreen(
    inputPasswordType: InputPasswordType,
    loginViewModel: LoginViewModel,
    onNextStep: () -> Unit,
    isLoginFragment: Boolean
) {
    var isRepeat by remember { mutableStateOf(false) }
    val errorPassword = remember { mutableStateOf(false) }
    val context = LocalContext.current

    AnimatedVisibility(
        visible = !isRepeat,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        FirstScreen(
            loginViewModel = loginViewModel,
            errorPassword = errorPassword,
            onNextStep = {
                if (inputPasswordType == InputPasswordType.SIGNUP) {
                    isRepeat = true
                    loginViewModel.passwordRepeat.value = ""
                } else {
                    if (isLoginFragment) {
                        loginViewModel.login(
                            context = context,
                            onErrorPassword = { errorPassword.value = true },
                            onMoveLoginDoneScreen = onNextStep
                        )
                    } else {
                        loginViewModel.reLogin(
                            context = context,
                            onErrorPassword = { errorPassword.value = true },
                            onMoveLoginDoneScreen = onNextStep
                        )
                    }
                }
            }
        )
    }
    if (inputPasswordType == InputPasswordType.SIGNUP) {
        AnimatedVisibility(
            visible = isRepeat,
            enter = slideInVertically(
                initialOffsetY = { it / 2 }
            ),
            exit = slideOutVertically()
        ) {
            SecondScreen(
                loginViewModel = loginViewModel,
                onNextStep = {
                    loginViewModel.signup(
                        context = context,
                        onMoveLoginDoneScreen = onNextStep
                    )
                }
            )
        }
    }
}

@Composable
private fun FirstScreen(
    loginViewModel: LoginViewModel,
    onNextStep: () -> Unit,
    errorPassword: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Spacer(modifier = Modifier.size(0.dp, 70.dp))
        Text(
            text = stringResource(id = R.string.msg_set_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            fontSize = dimensionResource(id = R.dimen.font_size_title_desc).value.sp,
            lineHeight = dimensionResource(id = R.dimen.font_size_title_desc).value.sp
        )
        Spacer(modifier = Modifier.size(0.dp, 20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeTextInput(
                value = loginViewModel.password.value,
                onValueChange = {
                    if (it.length <= 6 && it != loginViewModel.password.value) {
                        loginViewModel.password.value = it
                        errorPassword.value = false
                    }
                },
                isPassword = true,
                isError = errorPassword.value,
                errorMessage = {
                    Text(
                        text = stringResource(id = R.string.err_error_password),
                        color = MaterialTheme.colorScheme.error
                    )
                },
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = onNextStep,
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled = loginViewModel.isPossibleGoNext(SignupStep.InputPassword)
        )
    }
}

@Composable
private fun SecondScreen(
    loginViewModel: LoginViewModel,
    onNextStep: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Spacer(modifier = Modifier.size(0.dp, 70.dp))
        Text(
            text = stringResource(id = R.string.msg_set_password_repeat),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            fontSize = dimensionResource(id = R.dimen.font_size_title_desc).value.sp,
            lineHeight = dimensionResource(id = R.dimen.font_size_title_desc).value.sp
        )
        Spacer(modifier = Modifier.size(0.dp, 20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeTextInput(
                value = loginViewModel.passwordRepeat.value ?: "",
                onValueChange = {
                    if (it.length <= 6) {
                        loginViewModel.passwordRepeat.value = it
                    }
                },
                isPassword = true,
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = onNextStep,
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled = loginViewModel.isPossibleGoNext(SignupStep.InputPassword)
        )
    }
}
