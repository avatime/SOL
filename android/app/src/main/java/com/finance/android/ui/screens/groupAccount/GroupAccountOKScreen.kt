package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountOKScreen(navController: NavController, groupAccountViewModel: GroupAccountViewModel, modifier: Modifier) {
    Box{
        Column(modifier = modifier.fillMaxSize().background(Color.White)) {
            Text(text="히히 만들어ㄸ야 ><")
        }
    }
}