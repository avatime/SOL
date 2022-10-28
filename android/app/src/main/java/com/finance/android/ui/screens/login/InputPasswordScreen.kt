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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.CodeTextInput
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.fragments.LoginStep
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.LoginViewModel

@Composable
fun InputPasswordScreen(
    loginViewModel: LoginViewModel,
    onNextStep: () -> Unit
) {
    var isRepeat by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = !isRepeat,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        FirstScreen(
            loginViewModel = loginViewModel,
            onNextStep = {
                isRepeat = true
                loginViewModel.passwordRepeat.value = ""
            }
        )
    }
    AnimatedVisibility(
        visible = isRepeat,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically()
    ) {
        SecondScreen(
            loginViewModel = loginViewModel,
            onNextStep = onNextStep
        )
    }
}

@Composable
private fun FirstScreen(
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
            text = stringResource(id = R.string.msg_set_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
        Spacer(modifier = Modifier.size(0.dp, 20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeTextInput(
                value = loginViewModel.password.value,
                onValueChange = {
                    if (it.length <= 6) {
                        loginViewModel.password.value = it
                    }
                }
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = onNextStep,
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled = loginViewModel.isPossibleGoNext(LoginStep.InputPassword)
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
                .padding(dimensionResource(id = R.dimen.padding_medium))
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
                }
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = onNextStep,
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled = loginViewModel.isPossibleGoNext(LoginStep.InputPassword)
        )
    }
}
