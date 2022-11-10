package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.ui.theme.Typography
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun DuesMakeMoneyScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
        Row(
            modifier = Modifier.padding(32.dp)
        ) {
            Column() {
                Text(
                    text = "얼마를 보낼까요?",
                    style = Typography.headlineLarge
                )
                Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))
                Text(
                    text = "한 명당",
                    color = Color(R.color.noActiveColor)
                )
            }
        }

        TextInput(
            value = groupAccountViewModel.duesBalance.value.toString(),
            onValueChange = {
                if (1 <= it.length && it.length <= 20) {
                    groupAccountViewModel.duesBalance.value = Integer.parseInt(it)
                }
            },
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
                .padding(0.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { navController.navigate(Const.DUES_MEMBER_LIST) },
            modifier = Modifier
                .withBottomButton(),
            text = "확인",
            buttonType = ButtonType.ROUNDED,
        )
    }
}