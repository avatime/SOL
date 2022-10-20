package com.finance.android

import androidx.compose.runtime.Composable
import com.finance.android.ui.screens.MainScreen
import com.finance.android.ui.theme.FinanceTheme

@Composable
fun FinanceApp() {
    FinanceTheme {
        MainScreen()
    }
}
