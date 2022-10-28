package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AssetScreen() {
    Scaffold(
        topBar = {
            TopAppBar() {

            }
        }
    ) { ButtonDefaults.ContentPadding ->
        Box {
            Text(text = "AssetScreen")
        }
    }

}