package com.finance.android.ui.components

import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.finance.android.R

@Composable
fun BiometricDialog(callback: BiometricPrompt.AuthenticationCallback) {
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(stringResource(id = R.string.app_name))
        .setNegativeButtonText(stringResource(android.R.string.cancel))
        .build()

    val activity = LocalContext.current as FragmentActivity
    val executor = ContextCompat.getMainExecutor(activity)
    BiometricPrompt(activity, executor, callback).authenticate(promptInfo)
}
