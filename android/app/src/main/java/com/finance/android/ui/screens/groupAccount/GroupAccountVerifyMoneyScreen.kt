package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.finance.android.domain.dto.request.RemitDuesRequestDto
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccontVerifyMoneyScreen(
    navController: NavController,
    modifier: Modifier,
    groupAccountViewModel: GroupAccountViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(text = "${groupAccountViewModel.duesVal.value}를 보내겠습니까?")
        TextButton(
            onClick = {
                groupAccountViewModel.postPayDues(
                    RemitDuesRequestDto(
                        duesVal = groupAccountViewModel.duesVal.value,
                        duesId = groupAccountViewModel.duesId.value
                    ), onSuccess = {navController.navigate(Const.GROUP_ACCOUNT_MAIN_SCREEN)})
            },
            text = "확인",
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.withBottomButton()
        )


    }

}