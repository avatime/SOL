package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.HeaderAssetTabBar

@Composable
fun AssetFragment(navController: NavController, onClose: () -> Unit) {
    Scaffold(
        topBar = {
            BackHeaderBar(text = "자산", modifier = Modifier, onClickBack = onClose)
        },
    ) { innerPaddingModifier ->
        Box(modifier = Modifier
            .fillMaxHeight()
            .padding(top = innerPaddingModifier.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background))
        {
            HeaderAssetTabBar(modifier = Modifier, navController = navController)
        }
    }

}