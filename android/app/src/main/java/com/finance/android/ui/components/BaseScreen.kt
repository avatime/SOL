package com.finance.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.finance.android.R

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    loading: Boolean,
    loadingMessage: String? = null,
    error: Exception?,
    onError: () -> Unit,
    calculatedTopPadding: Dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = calculatedTopPadding)
            .fillMaxSize()
    ) {
        content()

        if (loading) {
            AnimatedLoading(text = loadingMessage)
        }

        if (error!=null) {
            CustomDialog(
                dialogType = DialogType.ERROR,
                dialogActionType = DialogActionType.ONE_BUTTON,
                title = stringResource(id = R.string.msg_server_error),
                onPositive = { onError() }
            )
        }
    }
}
