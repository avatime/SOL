package com.finance.android.ui.screens.login

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.LoginViewModel

@Composable
fun InputUserInfoScreen(
    loginViewModel: LoginViewModel,
    onNextStep: () -> Unit
) {
    var focus by remember { mutableStateOf(InputUserInfoStep.NAME) }
    var step by remember { mutableStateOf(InputUserInfoStep.NAME) }
    val values = arrayOf(loginViewModel.name, loginViewModel.birthday, loginViewModel.phoneNumber)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Spacer(modifier = Modifier.size(0.dp, 70.dp))
        Text(
            text = stringResource(id = focus.getTitleStringRes()),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
        InputUserInfoStep.values()
            .reversed()
            .forEach {
                InputComp(
                    visible = it.id <= step.id,
                    step = it,
                    focus = focus,
                    onFocusChanged = { f -> focus = f },
                    value = values[it.id].value,
                    onValueChange = { v -> values[it.id].value = v }
                )
            }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = {
                if (focus.id < step.id) {
                    focus = InputUserInfoStep.values()[focus.id + 1]
                } else if (step == InputUserInfoStep.PHONE_NUM) {
                    onNextStep()
                } else {
                    step = InputUserInfoStep.values()[step.id + 1]
                    focus = InputUserInfoStep.values()[focus.id + 1]
                }
            },
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled = loginViewModel.isPossibleGoNext(step)
        )
    }
}

enum class InputUserInfoStep(val id: Int) {
    NAME(0),
    BIRTHDAY(1),
    PHONE_NUM(2);

    @StringRes
    fun getTitleStringRes(): Int {
        return when (this) {
            NAME -> R.string.msg_input_name
            BIRTHDAY -> R.string.msg_input_birthday
            PHONE_NUM -> R.string.msg_input_phone_num
        }
    }

    @StringRes
    fun getLabelStringRes(): Int {
        return when (this) {
            NAME -> R.string.label_input_name
            BIRTHDAY -> R.string.label_input_birthday
            PHONE_NUM -> R.string.label_input_phone_num
        }
    }
}

@Composable
private fun InputComp(
    visible: Boolean,
    step: InputUserInfoStep,
    focus: InputUserInfoStep,
    onFocusChanged: (InputUserInfoStep) -> Unit,
    value: String,
    onValueChange: (String) -> Unit
) {
    AnimatedVisibility(visible = visible) {
        TextInput(
            value = value,
            onValueChange = onValueChange,
            focus = visible && step == focus,
            modifier = Modifier
                .onFocusChanged { onFocusChanged(step) }
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
                .padding(0.dp),
            label = stringResource(id = step.getLabelStringRes()),
            keyboardType = if (step == InputUserInfoStep.NAME) KeyboardType.Text else KeyboardType.Number
        )
    }
}
