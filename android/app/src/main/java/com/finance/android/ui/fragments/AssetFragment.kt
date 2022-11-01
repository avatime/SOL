package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.finance.android.ui.components.HeaderAssetTabBar

@Composable
fun AssetFragment(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar() {

            }
        }
    ) { innerPaddingModifier ->
        Box(modifier = Modifier.padding(innerPaddingModifier)) {
            HeaderAssetTabBar()
        }
    }

}