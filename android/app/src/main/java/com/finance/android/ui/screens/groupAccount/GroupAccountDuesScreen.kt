package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.DuesItem
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountDuesScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    fun launch() {
        groupAccountViewModel.postDuesHistory(groupAccountViewModel.paId.value)
    }
    LaunchedEffect(Unit) {
        launch()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        when (val response = groupAccountViewModel.duesHistoryData.value) {
            is Response.Failure -> androidx.compose.material.Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "")
            is Response.Success -> {
                response.data.forEach {
                    DuesItem(
                        name = it.duesName,
                        dueDate = it.dueData,
                        totalUser = it.totalUSer,
                        paidUser = it.paidUser,
                        duesVal = it.duesVal
                    )
                }
            }
        }

        TextButton(onClick = {navController.navigate(Const.DUES_MAKE_NAME_SCREEN) }, text = "", buttonType = ButtonType.ROUNDED)

    }
}