package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.showHistoryList
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountTradeDetailScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier = Modifier,
) {

    fun launch() {
        groupAccountViewModel.getDuesTradeHistory(groupAccountViewModel.paId.value)
    }

    LaunchedEffect(Unit) {
        launch()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        when (val response = groupAccountViewModel.duesTradeHistoryData.value) {

            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "조그만 기다려주세요!")
            is Response.Success -> {
                showHistoryList(modifier = Modifier.weight(1.0f),
                    historyList = List(response.data.size) { i -> response.data[i].toEntity() })













            }

        }//end of when


//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.weight(1f)
//        ) {
//            Spacer(modifier = Modifier.weight(0.3f))
//            TextButton(
//                onClick = { navController.navigate(Const.GROUP_ACCOUNT_INPUT_MONEY_SCREEN) },
//                text = "입금",
//                buttonType = ButtonType.ROUNDED
//            )
//            Spacer(modifier = Modifier.weight(0.3f))
//            TextButton(
//                onClick = { navController.navigate(Const.GROUP_ACCOUNT_INPUT_MONEY_SCREEN) },
//                text = "출금",
//                buttonType = ButtonType.ROUNDED
//            )
//            Spacer(modifier = Modifier.weight(0.3f))
//        }//end of Row
    }//end of column
}
