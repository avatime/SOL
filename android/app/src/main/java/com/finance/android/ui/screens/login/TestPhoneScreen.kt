package com.finance.android.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.ui.components.*
import com.finance.android.ui.fragments.SignupStep
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.LoginViewModel

@Composable
fun TestPhoneScreen(
    loginViewModel: LoginViewModel,
    onNextStep: () -> Unit
) {
    fun launch() {
        loginViewModel.loadRightCode()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Spacer(modifier = Modifier.size(0.dp, 70.dp))
        Text(
            text = stringResource(id = R.string.msg_test_phone_num),
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
            TextButton(
                onClick = { launch() },
                text = stringResource(id = R.string.btn_re_receive_test_phone_code),
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                ),
                buttonType = ButtonType.CIRCULAR,
                buttonColor = ButtonColor.WHITE,
                fontSize = dimensionResource(id = R.dimen.font_size_btn_small_text).value.sp
            )
            Spacer(modifier = Modifier.size(0.dp, 20.dp))
            CodeTextInput(
                value = loginViewModel.code.value,
                onValueChange = {
                    if (it.length <= 6) {
                        loginViewModel.code.value = it
                    }
                },
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_input).value.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        loginViewModel.showRightCode()
            ?.let {
                TransientSnackbar {
                    Text(
                        text = stringResource(id = R.string.msg_receive_test_phone_code, it),
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        TextButton(
            onClick = onNextStep,
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled = loginViewModel.isPossibleGoNext(SignupStep.TestPhone)
        )
    }
}
