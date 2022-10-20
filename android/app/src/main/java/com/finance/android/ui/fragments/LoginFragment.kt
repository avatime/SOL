package com.finance.android.ui.fragments

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.utils.ext.withBottomButton

@Composable
fun LoginFragment() {


    InputUserInfoScreen()
}

private enum class InputUserInfoStep {
    NAME,
    BIRTHDAY,
    PHONE_NUM;

    @StringRes
    fun getTitleStringRes(): Int {
        return when (this) {
            NAME -> R.string.msg_input_name
            BIRTHDAY -> R.string.msg_input_birthday
            PHONE_NUM -> R.string.msg_input_phone_num
        }
    }
}

@Preview
@Composable
fun InputUserInfoScreen() {
    var focusStep by remember { mutableStateOf(InputUserInfoStep.NAME) }
    var step by remember { mutableStateOf(InputUserInfoStep.NAME) }

    var name by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(0.dp, 70.dp))
        Text(
            text = stringResource(id = focusStep.getTitleStringRes()),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
        TextInput(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            label = stringResource(id = R.string.label_input_name)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            onClick = { /*TODO*/ },
            text = stringResource(id = R.string.btn_confirm),
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton()
        )
    }
}