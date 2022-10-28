package com.finance.android.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.ui.components.HeaderRemitTabBar

@Preview
@Composable
fun HomeScreen() {
  Spacer(modifier = Modifier.padding(100.dp))
  HeaderRemitTabBar()
}