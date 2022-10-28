package com.finance.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun BackHeaderBar(text: String, modifier: Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {

        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            IconButton(onClick = {
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "back")


            }
        }

        Column(verticalArrangement = Arrangement.Center) {
            Text(text = text, textAlign = TextAlign.Center)
        }


    }

}

//@Composable
//fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
//    val currentOnBack by rememberUpdatedState(onBack)
//    val backCallback = remember {
//        object : OnBackPressedCallback(enabled) {
//            override fun handleOnBackPressed() {
//                currentOnBack()
//            }
//        }
//    }
//    SideEffect {
//        backCallback.isEnabled = enabled
//    }
//    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
//        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
//    }.onBackPressedDispatcher
//    val lifecycleOwner = LocalLifecycleOwner.current
//    DisposableEffect(lifecycleOwner, backDispatcher) {
//        backDispatcher.addCallback(lifecycleOwner, backCallback)
//
//        onDispose {
//            backCallback.remove()
//        }
//    }
//}

