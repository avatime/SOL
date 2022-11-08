package com.finance.android.ui.screens.groupAccount


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountMainScreen(navController: NavController, groupAccountViewModel: GroupAccountViewModel) {

    GroupAccountMakeScreen(navController = navController, groupAccountViewModel = groupAccountViewModel)

}