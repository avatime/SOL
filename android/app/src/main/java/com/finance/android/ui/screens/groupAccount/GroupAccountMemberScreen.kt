package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.GroupAccountMemberListItem
import com.finance.android.utils.Response
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountMemberScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel
) {
    fun launch() {
        groupAccountViewModel.getGroupAccountMember(groupAccountViewModel.paId)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    when (val response = groupAccountViewModel.groupAccountMemberData.value) {
        is Response.Failure -> Text(text = "실패")
        is Response.Loading -> AnimatedLoading(text = "가져오고 있어용")
        is Response.Success -> {
            LazyColumn {
                items(count = response.data.size, key = { it }, itemContent = {
                    val item = response.data[it]
                    GroupAccountMemberListItem(
                        img = item.pfImg,
                        name = item.userName,
                        type = item.type
                    )
                })
            }

        }
    }

}