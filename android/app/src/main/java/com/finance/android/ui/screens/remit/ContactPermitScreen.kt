package com.finance.android.ui.screens.remit


import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.viewmodels.RemitViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState






@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactScreen(remitViewModel: RemitViewModel, navController: NavController) {



    val permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)




    when {
        permissionState.status.isGranted -> {
            ContactListScreen(remitViewModel = remitViewModel, navController = navController)
        }
        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                //verticalArrangement = Arrangement.Center
            ) {

                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_remit_contact))
                Spacer(modifier = Modifier.padding(40.dp))
                LottieAnimation(
                    composition = composition,

                    modifier = Modifier.size(100.dp),
                    iterations = LottieConstants.IterateForever,
                )



                Spacer(modifier = Modifier.padding(20.dp))

                Text(text = "연락처를 불러올까요?")
                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(
                    onClick = { permissionState.launchPermissionRequest()},
                    text ="불러오기",
                    buttonType = ButtonType.ROUNDED,
                )
            }
        }
    }
}



