package com.finance.android.ui.screens.remit

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.finance.android.ui.components.ButtonType
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.RemitViewModel

@Composable
fun RemitOKScreen(remitViewModel: RemitViewModel, navController : NavController) {
    val moneyValue = "1332342"
    Column() {
        Text(text = "${moneyValue}원")
        Text(text = "송금완료")
        com.finance.android.ui.components.TextButton(
            onClick = {
                navController.navigate(Const.INPUT_RECEIVER_SCREEN)
            },
            text = "완료",
            modifier = Modifier.withBottomButton(),
            buttonType = ButtonType.ROUNDED,

            )
    }
}