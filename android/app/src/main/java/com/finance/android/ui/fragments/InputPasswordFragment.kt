package com.finance.android.ui.fragments

import android.content.pm.PackageManager
import android.os.Build
import androidx.biometric.BiometricPrompt
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.R
import com.finance.android.datastore.UserStore
import com.finance.android.ui.components.*
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.InputPasswordViewModel

@Composable
fun InputPasswordFragment(
    inputPasswordViewModel: InputPasswordViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    var successBiometric by remember { mutableStateOf(false) }
    var errorBiometric by remember { mutableStateOf(false) }

    var useBio by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        UserStore(context).getValue(UserStore.KEY_USE_BIO).collect {
            useBio = it == "1"
        }
    }

    if (!errorBiometric && !successBiometric && useBio) {
        BiometricDialog(
            callback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    successBiometric = true
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    errorBiometric = true
                }
            }
        )
    }

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                LocalContext.current.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
            ) {
                TextButton(
                    onClick = { inputPasswordViewModel.onClickUserBioButton() },
                    text = stringResource(id = R.string.msg_info_bio_title),
                    buttonType = ButtonType.CIRCULAR,
                    buttonColor = if (inputPasswordViewModel.useBio.value) ButtonColor.PRIMARY else ButtonColor.WHITE,
                    fontSize = dimensionResource(id = R.dimen.font_size_btn_small_text).value.sp
                )
                Spacer(modifier = Modifier.size(0.dp, 20.dp))
            }
            CodeTextInput(
                value = inputPasswordViewModel.password.value,
                onValueChange = {
                    inputPasswordViewModel.onValueChange(it)
                },
                isPassword = true,
                isError = inputPasswordViewModel.errorPassword.value,
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
            onClick = { inputPasswordViewModel.onClickNextButton(onSuccess) },
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton(),
            enabled = inputPasswordViewModel.enableOnClickNextButton()
        )
    }

    if (inputPasswordViewModel.showBioInfoDialog.value) {
        CustomDialog(
            dialogType = DialogType.INFO,
            dialogActionType = DialogActionType.ONE_BUTTON,
            title = stringResource(id = R.string.msg_info_bio_title),
            subTitle = stringResource(id = R.string.msg_info_bio_body),
            onPositive = { inputPasswordViewModel.showBioInfoDialog.value = !inputPasswordViewModel.showBioInfoDialog.value }
        )
    }
}
