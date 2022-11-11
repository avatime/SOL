package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.GroupAccountMemberListItem
import com.finance.android.utils.Response
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountMemberScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {

    fun launch() {
        groupAccountViewModel.getGroupAccountMember(groupAccountViewModel.paId.value)
    }

    LaunchedEffect(Unit) {
        launch()
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        when (val response = groupAccountViewModel.groupAccountMemberData.value) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "가져오고 있어요")
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

}