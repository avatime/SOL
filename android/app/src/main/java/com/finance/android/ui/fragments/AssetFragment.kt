package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.HeaderAssetTabBar

@Composable
fun AssetFragment(navController: NavController) {
    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text(text = "자산", fontWeight = FontWeight.Bold)},backgroundColor = Color(R.color.light_gray),
//                navigationIcon = {
//                    IconButton(onClick = {}) {
//                        Icon(Icons.Filled.ArrowBack, "backIcon")
//                    }
//                },)
//        },

    ) { innerPaddingModifier ->
        Box(modifier = Modifier
            .fillMaxHeight()
            .padding(innerPaddingModifier)
            .verticalScroll(rememberScrollState())
            .background(color = Color(R.color.light_gray)))
        {
            HeaderAssetTabBar(modifier = Modifier, navController = navController)
        }
    }

}