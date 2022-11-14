package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.screens.groupAccount.*

import com.finance.android.utils.Const
import com.finance.android.viewmodels.GroupAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupAccountFragment(
    navController: NavController, groupAccountViewModel: GroupAccountViewModel = hiltViewModel()
) {
    val innerNavController = rememberNavController()
    Scaffold(topBar = {
        BackHeaderBar(text = "", onClickBack = {
            if (!groupAccountViewModel.isBackToMain.value) innerNavController.popBackStack() else
                navController.popBackStack()
        })
    }) { innerPaddingModifier ->
        val modifier = Modifier.padding(top = innerPaddingModifier.calculateTopPadding())
        NavHost(
            navController = innerNavController, startDestination = Const.GROUP_ACCOUNT_MAIN_SCREEN
        ) {
            composable(Const.GROUP_ACCOUNT_MAIN_SCREEN) {
                groupAccountViewModel.isBackToMain.value = true
                GroupAccountMainScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }
            composable(Const.GROUP_ACCOUNT_MAKE_SCREEN) {
                groupAccountViewModel.isBackToMain.value = false
                GroupAccountMakeScreen(
                    navController = innerNavController,
                    modifier = modifier
                )
            }

            composable(Const.GROUP_ACCOUNT_NAME_SCREEN) {
                groupAccountViewModel.isBackToMain.value = false
                GroupAccountNameScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }

            composable(Const.GROUP_ACCOUNT_FRIEND_SCREEN) {
                groupAccountViewModel.isBackToMain.value = false
                GroupAccountFriendScreen(
                    groupAccountViewModel = groupAccountViewModel,
                    navController = innerNavController,
                    modifier = modifier
                )
            }

            composable(
                route = "${Const.GROUP_ACCOUNT_DETAIL_SCREEN}/{paId}",
                arguments = listOf(
                    navArgument(name = "paId") { type = NavType.IntType },
                )
            ) {
                groupAccountViewModel.isBackToMain.value = false
                GroupAccountDetailScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier,
                )

            }

            composable(Const.GROUP_ACCOUNT_COMPLETED) {
                groupAccountViewModel.isBackToMain.value = false
                GroupAccountOKScreen(
                    navController = innerNavController,
                    modifier = modifier
                )
            }


            composable(Const.DUES_MAKE_NAME_SCREEN) {
                groupAccountViewModel.isBackToMain.value = false
                DuesMakeNameScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }

            composable(
                "${Const.GROUP_ACCOUNT_INPUT_MONEY_SCREEN}/{duesVal}",
                arguments = listOf(navArgument(name = "duesVal") { type = NavType.IntType })
            ) {
                groupAccountViewModel.isBackToMain.value = false
                GroupAccountInputMoneyScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }

            composable(Const.DUES_MAKE_MONEY_SCREEN) {
                groupAccountViewModel.isBackToMain.value = false
                DuesMakeMoneyScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }

            composable(Const.DUES_MEMBER_LIST) {
                groupAccountViewModel.isBackToMain.value = false
                DuesMemberListScreen(
                    groupAccountViewModel = groupAccountViewModel,
                    navController = innerNavController,
                    modifier = modifier
                )
            }

            composable(Const.GROUP_ACCOUNT_VERIFY_MONEY_SCREEN) {
                groupAccountViewModel.isBackToMain.value = false
                GroupAccountVerifyMoneyScreen(
                    navController = innerNavController,
                    modifier = modifier,
                    groupAccountViewModel = groupAccountViewModel
                )
            }


        }
    }
}