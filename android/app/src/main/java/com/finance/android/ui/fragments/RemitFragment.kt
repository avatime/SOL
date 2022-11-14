package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.HeaderRemitTabBar
import com.finance.android.ui.screens.remit.InputMoneyScreen
import com.finance.android.ui.screens.remit.RemitOKScreen
import com.finance.android.utils.Const
import com.finance.android.viewmodels.RemitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemitFragment(
    navController: NavController,
    remitViewModel: RemitViewModel = hiltViewModel()
) {
    val innerNavController = rememberNavController()
    Scaffold(
        containerColor = Color.White,
        topBar = {
            if (!remitViewModel.enabledBackHeader.value) {
                BackHeaderBar(
                    text = "",
                    modifier = Modifier,
                    onClickBack = {
                        if (remitViewModel.isBackToMain.value) {
                            navController.popBackStack()
                        } else {
                            innerNavController.popBackStack()
                        }
                    },
                    backgroundColor = MaterialTheme.colorScheme.surface
                )
            }
        }

    ) { innerPaddingModifier ->
        val modifier = Modifier.padding(top = innerPaddingModifier.calculateTopPadding())

        NavHost(
            navController = innerNavController,
            startDestination = Const.INPUT_RECEIVER_SCREEN
        ) {
            composable(Const.INPUT_RECEIVER_SCREEN) {
                remitViewModel.isBackToMain.value = true
                HeaderRemitTabBar(
                    modifier = modifier,
                    remitViewModel = remitViewModel,
                    navController = innerNavController
                )
            }
            composable(Const.INPUT_MONEY_SCREEN) {
                remitViewModel.isBackToMain.value = false
                InputMoneyScreen(
                    modifier = modifier,
                    remitViewModel = remitViewModel,
                    navController = innerNavController
                )
            }
            composable(
                "${Const.REMIT_OK_SCREEN}/{sendMoney}/{content}",
                arguments = listOf(
                    navArgument("sendMoney") { type = NavType.IntType },
                    navArgument("content") { type = NavType.StringType }
                )
            ) {
                remitViewModel.isBackToMain.value = false
                remitViewModel.enabledBackHeader.value = true
                RemitOKScreen(
                    sendMoney = it.arguments!!.getInt("sendMoney"),
                    content = it.arguments!!.getString("content")!!,
                    onClose = { navController.popBackStack() }
                )
            }
        }
    }
}
