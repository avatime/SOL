package com.finance.android.ui.screens.groupAccount

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.ui.theme.Typography
import com.finance.android.utils.Const
import com.finance.android.utils.NumberCommaTransformation
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import java.util.regex.Pattern

@Composable
fun DuesMakeMoneyScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    LaunchedEffect(Unit){
        groupAccountViewModel.duesBalance.value = ""
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
        Row(
            modifier = Modifier.padding(32.dp)
        ) {
            Column {
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
            value = groupAccountViewModel.duesBalance.value,
            onValueChange = {
                if (!Pattern.matches("^[0-9]*$", it)) return@TextInput
                if (it.isNotEmpty() && it.toLong() > Int.MAX_VALUE) return@TextInput
                groupAccountViewModel.duesBalance.value = if (it.isEmpty()) "" else it.toInt().toString()
            },
            keyboardType = KeyboardType.Number,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
                .padding(0.dp),
            textStyle = TextStyle().copy(fontSize = 40.sp),
            visualTransformation = NumberCommaTransformation()
        )

        Spacer(modifier = Modifier.weight(1f))
        AnimatedVisibility(
            visible = groupAccountViewModel.duesBalance.value.isNotEmpty() && groupAccountViewModel.duesBalance.value != "0",
            enter = slideInVertically(initialOffsetY = { it / 2 }),
            exit = slideOutVertically(targetOffsetY = { 2 * it })
        ) {
            TextButton(
                onClick = {
                    if (groupAccountViewModel.duesBalance.value.isNotEmpty()) {
                        navController.navigate(Const.DUES_DATE_PICK_SCREEN)
                    }
                },
                modifier = Modifier
                    .withBottomButton(),
                text = "다음",
                buttonType = ButtonType.ROUNDED,
            )
        }
    }
}