package com.finance.android.ui.fragments

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.finance.android.datastore.UserStore
import kotlinx.coroutines.launch

@Composable
fun LoginFragment() {
    val scope = rememberCoroutineScope()
    val userStore = UserStore(LocalContext.current)
    Button(onClick = {
        scope.launch {
            userStore.setAccessToken("!!")
        }
    }) {

    }
}