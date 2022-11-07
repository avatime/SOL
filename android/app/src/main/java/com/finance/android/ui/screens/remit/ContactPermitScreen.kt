package com.finance.android.ui.screens


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.finance.android.MainActivity
import com.finance.android.R
import com.finance.android.domain.dto.response.Contact
import com.finance.android.ui.components.*
import com.finance.android.ui.screens.remit.ContactListScreen
import com.finance.android.viewmodels.RemitViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState






@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactScreen(remitViewModel: RemitViewModel, navController: NavController) {



    val permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)




    when {
        permissionState.hasPermission -> {
            ContactListScreen(remitViewModel = remitViewModel, navController = navController)

        }
        permissionState.shouldShowRationale -> {

        }
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
            val isShow = remember {
                mutableStateOf(false)
            }
            if(!isShow.value) {
                CustomDialog(dialogType = DialogType.WARNING, dialogActionType = DialogActionType.ONE_BUTTON, title = "접근 불가", subTitle = "앱의 설정에서 연락처 접근을 허용해주세요",onPositive = {isShow.value = true})
            }


        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center
    ) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_remit_contact))
        val progress by animateLottieCompositionAsState(composition)
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



