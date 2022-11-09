package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.screens.ContactScreen
import com.finance.android.ui.screens.group.GroupAccountMakeScreen
import com.finance.android.ui.screens.groupAccount.GroupAccountDetailScreen
import com.finance.android.ui.screens.groupAccount.GroupAccountFriendScreen
import com.finance.android.ui.screens.groupAccount.GroupAccountMainScreen
import com.finance.android.ui.screens.groupAccount.GroupAccountNameScreen
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import com.finance.android.viewmodels.RemitViewModel

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
    ) { innerPaddingModifier ->
        val modifier = Modifier.padding(top = innerPaddingModifier.calculateTopPadding())

        NavHost(
            navController = innerNavController,
            startDestination = Const.GROUP_ACCOUNT_MAIN_SCREEN
        ) {
            composable(Const.GROUP_ACCOUNT_MAIN_SCREEN) {
                GroupAccountMainScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel
                )
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
                GroupAccountFriendScreen(groupAccountViewModel = groupAccountViewModel, navController = innerNavController)
            }

            composable(Const.GROUP_ACCOUNT_DETAIL_SCREEN) {
                GroupAccountDetailScreen(navController = innerNavController)

            }



        }
    }


}
