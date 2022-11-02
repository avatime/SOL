package com.finance.android.ui.screens

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.finance.android.utils.Const

@Composable
fun ProductScreen(navController: NavController) {
    Button(
        onClick = { navController.navigate(Const.Routes.ADD_ASSET) }
    ) {
    }
}
