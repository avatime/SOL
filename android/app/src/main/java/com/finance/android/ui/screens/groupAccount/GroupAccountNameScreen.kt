package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountNameScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    LaunchedEffect(Unit){
        groupAccountViewModel.name.value=""
    }
    var isError = remember { mutableStateOf(false) }
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
            Column {
                Text(
                    text = "모두의 통장 이름을",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "입력해주세요",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        TextInput(
            value = groupAccountViewModel.name.value,
            onValueChange = {
                if (it.length < 20) {
                    groupAccountViewModel.name.value = it
                    isError.value = false
                } else if (it.length == 20) {
                    isError.value = true
                }

            },
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
                .padding(0.dp),
            isError = isError.value
        )
        if (isError.value) {
            Text(
                text = "20글자 이내로 부탁드려요",
                color = androidx.compose.material.MaterialTheme.colors.error,
                style = androidx.compose.material.MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 30.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = {
                if (groupAccountViewModel.name.value.isNotEmpty()) {
                    navController.navigate(
                        Const.GROUP_ACCOUNT_FRIEND_SCREEN
                    )
                }
            },
            modifier = Modifier
                .withBottomButton(),
            text = "다음",
            buttonType = ButtonType.ROUNDED,
        )
    }
}