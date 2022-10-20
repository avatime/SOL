package com.finance.android

import androidx.compose.runtime.Composable
import com.finance.android.ui.fragments.MainFragment
import com.finance.android.ui.theme.FinanceTheme

@Composable
fun FinanceApp() {
    FinanceTheme {
        MainFragment()
    }
}
