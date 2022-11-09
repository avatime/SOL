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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    groupAccountViewModel: GroupAccountViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
        Row(
            modifier = Modifier.padding(32.dp)
        ) {
            Column() {
                Text(
                    text = "모두의 통장 이름을",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "입력해주세용",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        TextInput(
            value = groupAccountViewModel.name.value,
            onValueChange = {
                if (it.length <= 20){
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
            text = "다음",
            buttonType = ButtonType.ROUNDED,
        )
    }
}