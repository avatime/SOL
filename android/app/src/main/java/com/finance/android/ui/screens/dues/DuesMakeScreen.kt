package com.finance.android.ui.screens.dues

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.viewmodels.GroupAccountViewModel
import com.finance.android.ui.theme.Typography
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton

@Composable
fun DuesMakeScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
){
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
                    text = "회비 이름을 입력하세요",
                    style = Typography.headlineLarge
                )

            }
        }

        TextInput(
            value = groupAccountViewModel.name.value,
            onValueChange = {
                if (1 <= it.length && it.length <= 20){
                    groupAccountViewModel.name.value = it
                }
            },
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
                .padding(0.dp)
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { navController.navigate(Const.GROUP_ACCOUNT_FRIEND_SCREEN) },
            modifier = Modifier
                .withBottomButton(),
            text = "확인",
            buttonType = ButtonType.ROUNDED,
        )
    }
}