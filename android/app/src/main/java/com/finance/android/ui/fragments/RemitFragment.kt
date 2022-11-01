package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.HeaderRemitTabBar
import com.finance.android.ui.screens.*
import com.finance.android.utils.Const
import com.finance.android.viewmodels.RemitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RemitFragment(
    remitViewModel: RemitViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    Scaffold(containerColor = Color.White,
        topBar = {
            BackHeaderBar(text = "", modifier = Modifier)
        }


    ) { innerPaddingModifier ->
        val modifier = Modifier.padding(top = innerPaddingModifier.calculateTopPadding())

        HeaderRemitTabBar(
            modifier = modifier,
            remitViewModel = remitViewModel
        )

    }

}

