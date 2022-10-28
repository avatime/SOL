package com.finance.android.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.ext.withBottomButton

@Composable
fun LoginDoneScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(0.dp, 70.dp))
        Text(
            text = stringResource(id = R.string.msg_success_login)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = { },
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton()
        )
    }
}
