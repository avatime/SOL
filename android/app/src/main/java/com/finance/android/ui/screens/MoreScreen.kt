package com.finance.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton


@Composable
fun MoreScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Button(
            onClick = {
                navController.navigate(Const.Routes.ATTENDANCE)
            },
            modifier = Modifier.withBottomButton()
        ) {
            Text("여기는 MoreScreen 입니다람쥐")
        }
    }
}