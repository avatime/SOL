package com.finance.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance.android.R
import kotlinx.coroutines.delay

@Composable
fun TransientSnackbar(
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = SnackbarDefaults.backgroundColor,
    contentColor: Color = MaterialTheme.colorScheme.surface,
    elevation: Dp = 6.dp,
    autoDismiss: Boolean = true,
    timeout: Int = 4_000,
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var show by remember { mutableStateOf(true) }

    if (show) {
        Snackbar(
            modifier = modifier.fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            action = action,
            actionOnNewLine = actionOnNewLine,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            elevation = elevation
        ) {
            content()
        }
    }

    if (autoDismiss && show) {
        LaunchedEffect(Unit) {
            delay(timeout.toLong())
            show = false
            onDismiss?.invoke()
        }
    }
}
