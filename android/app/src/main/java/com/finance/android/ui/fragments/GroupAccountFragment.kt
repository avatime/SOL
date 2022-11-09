package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.screens.groupAccount.*

import com.finance.android.utils.Const
import com.finance.android.viewmodels.GroupAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupAccountFragment(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel = hiltViewModel()
) {
    val innerNavController = rememberNavController()
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "",
                onClickBack = { innerNavController.popBackStack() }
            )
        }
    ) {
        innerPaddingModifier ->
        val modifier = Modifier.padding(top = innerPaddingModifier.calculateTopPadding())
    }

    NavHost(
        navController = innerNavController,
        startDestination = Const.GROUP_ACCOUNT_MAIN_SCREEN
    ) {
        composable(Const.GROUP_ACCOUNT_MAIN_SCREEN){

            GroupAccountMainScreen( navController = innerNavController, groupAccountViewModel = groupAccountViewModel)
        }


        composable(Const.GROUP_ACCOUNT_MAKE_SCREEN){
            GroupAccountMakeScreen(
                navController = innerNavController,
                groupAccountViewModel = groupAccountViewModel
            )
        }

        composable(Const.GROUP_ACCOUNT_NAME_SCREEN){
            GroupAccountNameScreen(
                navController = innerNavController,
                groupAccountViewModel = groupAccountViewModel
            )
        }

        composable(Const.GROUP_ACCOUNT_FRIEND_SCREEN){
            GroupAccountFriendScreen(
                groupAccountViewModel = groupAccountViewModel,
                navController = innerNavController
            )
        }

        composable(Const.GROUP_ACCOUNT_DETAIL_SCREEN) {
            GroupAccountDetailScreen(navController = innerNavController)

        }

        composable(Const.GROUP_ACCOUNT_DETAIL_SCREEN){
            GroupAccountDetailScreen(navController = innerNavController, groupAccountViewModel = groupAccountViewModel)
        }
    }
}