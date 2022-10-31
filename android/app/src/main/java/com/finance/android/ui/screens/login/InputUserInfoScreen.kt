package com.finance.android.ui.screens.login

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.ui.fragments.SignupStep
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.LoginViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun InputUserInfoScreen(
    loginViewModel: LoginViewModel,
    onNextStep: () -> Unit,
    setInputPasswordType: (inputPasswordType: InputPasswordType) -> Unit
) {
    var focus by remember { mutableStateOf(InputUserInfoStep.NAME) }
    var step by remember { mutableStateOf(InputUserInfoStep.NAME) }
    var usedPhoneNumber by remember { mutableStateOf(false) }
    var invalidPhoneNumber by remember { mutableStateOf(false) }
    var invalidBirthday by remember { mutableStateOf(false) }

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
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            fontSize = dimensionResource(id = R.dimen.font_size_title_desc).value.sp,
        )
        AnimatedVisibility(visible = InputUserInfoStep.PHONE_NUM.id <= step.id) {
            TextInput(
                value = loginViewModel.phoneNumber.value,
                onValueChange = {
                    if (it.length <= 11) {
                        loginViewModel.phoneNumber.value = it
                    }
                },
                isError = usedPhoneNumber || invalidPhoneNumber,
                errorMessage = {
                    if (usedPhoneNumber || invalidPhoneNumber) {
                        Text(
                            text = stringResource(
                                id = if (invalidPhoneNumber) {
                                    R.string.err_invalid_phone_number
                                } else R.string.err_used_phone_number
                            )
                        )
                    }
                },
                focus = step.id == InputUserInfoStep.PHONE_NUM.id && step == focus,
                modifier = Modifier
                    .onFocusChanged { focus = InputUserInfoStep.PHONE_NUM }
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxWidth()
                    .padding(0.dp),
                label = stringResource(id = R.string.label_input_phone_num),
                keyboardType = KeyboardType.Phone,

                placeholder = {
                    Text(
                        text = stringResource(id = R.string.label_input_phone_num),
                        fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp,
                        color = Disabled
                    )
                },
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp,
                ),
                visualTransformation = {
                    val trimmed = if (it.text.length >= 11) it.text.substring(0..10) else it.text
                    var out = ""
                    for (i in trimmed.indices) {
                        out += trimmed[i]
                        if (it.text.length == 11 && (i == 2 || i == 6)) {
                            out += "-"
                        } else if (it.text.length < 11 && (i == 2 || i == 5)) {
                            out += "-"
                        }
                    }

                    val creditCardOffsetTranslator = object : OffsetMapping {
                        override fun originalToTransformed(offset: Int): Int {
                            if (offset <= 2) return offset
                            if (offset <= if (it.text.length == 11) 6 else 5) return offset + 1
                            if (offset <= if (it.text.length == 11) 11 else 10) return offset + 2
                            return 13
                        }

                        override fun transformedToOriginal(offset: Int): Int {
                            if (offset <= 3) return offset
                            if (offset <= if (it.text.length == 11) 8 else 7) return offset - 1
                            if (offset <= if (it.text.length == 11) 13 else 12) return offset - 2
                            return 11
                        }
                    }

                    TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
                }
            )
        }
        AnimatedVisibility(visible = InputUserInfoStep.BIRTHDAY.id <= step.id) {
            TextInput(
                value = loginViewModel.birthday.value,
                onValueChange = {
                    if (it.length <= 6) {
                        loginViewModel.birthday.value = it
                    }
                },
                focus = step.id == InputUserInfoStep.BIRTHDAY.id && step == focus,
                modifier = Modifier
                    .onFocusChanged { focus = InputUserInfoStep.BIRTHDAY }
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxWidth()
                    .padding(0.dp),
                label = stringResource(id = R.string.label_input_birthday),
                keyboardType = KeyboardType.Number,
                isError = invalidBirthday,
                errorMessage = {
                    if (invalidBirthday) {
                        Text(text = stringResource(id = R.string.err_invalid_birthday))
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.label_input_birthday),
                        fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp,
                        color = Disabled
                    )
                },
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp,
                )
            )
        }
        AnimatedVisibility(visible = InputUserInfoStep.NAME.id <= step.id) {
            TextInput(
                value = loginViewModel.name.value,
                onValueChange = {
                    if (it.length <= 10) {
                        loginViewModel.name.value = it
                    }
                },
                focus = step.id == InputUserInfoStep.NAME.id && step == focus,
                modifier = Modifier
                    .onFocusChanged { focus = InputUserInfoStep.NAME }
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxWidth()
                    .padding(0.dp),
                label = stringResource(id = R.string.label_input_name),
                keyboardType = KeyboardType.Text,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.label_input_name),
                        fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp,
                        color = Disabled
                    )
                },
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp,
                )
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = {
                if (!loginViewModel.isPossibleGoNext(SignupStep.InputUserInfo, step)) {
                    if (step == InputUserInfoStep.PHONE_NUM) {
                        invalidPhoneNumber = true
                    } else if (step == InputUserInfoStep.BIRTHDAY) {
                        invalidBirthday = true
                    }
                    return@TextButton
                }

                invalidPhoneNumber = false
                invalidBirthday = false
                if (focus.id < step.id) {
                    focus = InputUserInfoStep.values()[focus.id + 1]
                } else if (step == InputUserInfoStep.PHONE_NUM) {
                    loginViewModel.checkUser(
                        onMoveSignupScreen = {
                            setInputPasswordType(InputPasswordType.SIGNUP)
                            onNextStep()
                        },
                        onUsedPhoneNumber = {
                            usedPhoneNumber = true
                        },
                        onMoveLoginScreen = {
                            setInputPasswordType(InputPasswordType.LOGIN)
                            onNextStep()
                            onNextStep()
                        }
                    )
                } else {
                    step = InputUserInfoStep.values()[step.id + 1]
                    focus = InputUserInfoStep.values()[focus.id + 1]
                }
            },
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled =
            if (step == InputUserInfoStep.BIRTHDAY || step == InputUserInfoStep.PHONE_NUM) {
                true
            } else {
                loginViewModel.isPossibleGoNext(SignupStep.InputUserInfo, step)
            }
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
}
