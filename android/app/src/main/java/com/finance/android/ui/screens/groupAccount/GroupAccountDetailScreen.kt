package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.finance.android.ui.components.GroupAccountHeaderTabBar
import com.finance.android.viewmodels.GroupAccountViewModel
import java.util.NavigableMap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupAccountDetailScreen(navController: NavController, groupAccountViewModel: GroupAccountViewModel, modifier: Modifier) {
    Scaffold(topBar = {
        GroupAccountHeaderTabBar(navController = navController, groupAccountViewModel = groupAccountViewModel)

    }) {contentPadding->
        Box(modifier = modifier.padding(contentPadding)) {


        }


    }

}