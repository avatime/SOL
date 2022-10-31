package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.finance.android.datastore.UserStore
import com.finance.android.utils.Const

@Composable
fun SplashFragment(navController: NavController) {
    val accessToken = UserStore(LocalContext.current).getAccessToken

    LaunchedEffect(Unit) {
        accessToken.collect {
            if (it.isEmpty()) {
                navController.navigate(Const.Routes.LOGIN) {
                    popUpTo(Const.Routes.SPLASH) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Const.Routes.MAIN) {
                    popUpTo(Const.Routes.SPLASH) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "SplashFragment")
    }
}