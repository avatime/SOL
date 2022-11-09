package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance.android.ui.components.BackHeaderBar

@Composable
fun InsuranceDetailFragment(
    id: Int,
    name: String,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            BackHeaderBar(text = "보험입니다", modifier = Modifier, onClickBack = onClose)
        }
    ) { innerPaddingModifier ->
        Column(
            modifier = Modifier.padding(top = innerPaddingModifier.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
        ) {

        }
    }
}