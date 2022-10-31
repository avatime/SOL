package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.HeaderRemitTabBar
import com.finance.android.ui.screens.*
import com.finance.android.utils.Const

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RemitFragment() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
           BackHeaderBar(text = "", modifier = Modifier )
        }


    ) { innerPaddingModifier ->
        NavHost(
            navController = navController,
            startDestination = Const.RECO_SCREEN,
            modifier = Modifier.padding(innerPaddingModifier)
        ) {
            composable(Const.RECO_SCREEN) {
                RecoScreen()
            }
            composable(Const.ACCOUNT_SCREEN) {
                AccountScreen()
            }
            composable(Const.CONTACT_SCREEN) {
                ContactScreen()
            }


        }

    }

   HeaderRemitTabBar()
}

