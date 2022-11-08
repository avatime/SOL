package com.finance.android.ui.fragments

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.finance.android.R
import com.finance.android.datastore.WalkStore
import com.finance.android.services.WalkService
import com.finance.android.ui.components.*
import com.finance.android.ui.screens.WalkScreen
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.ext.withBottomButton
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PedometerFragment(
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        CustomDialog(
            dialogType = DialogType.WARNING,
            dialogActionType = DialogActionType.TWO_BUTTON,
            title = "신체활동 권한을 허용해주세요",
            positiveText = "허용하기",
            onPositive = {
                val i = Intent(ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    addCategory(Intent.CATEGORY_DEFAULT)
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                }
                context.startActivity(i)
                showDialog.value = false
            },
            negativeText = "취소",
            onNegative = {
                showDialog.value = false
            }
        )
    }

    if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
        val manager =
            LocalContext.current.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val notiState = remember { mutableStateOf(manager.areNotificationsEnabled()) }

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    notiState.value = manager.areNotificationsEnabled()
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        if (!notiState.value) {
            NoNotificationScreen(
                onClose = onClose,
                onPositive = {
                    val i = Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        addCategory(Intent.CATEGORY_DEFAULT)
                        data = Uri.parse("package:${context.packageName}")
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    }
                    context.startActivity(i)
                }
            )
            return
        }
    }

    val activeWalkCounter = remember { mutableStateOf<Boolean?>(null) }
    LaunchedEffect(Unit) {
        WalkStore(context).isActive().collect {
            activeWalkCounter.value = it
        }
    }

    if (activeWalkCounter.value == null) {
        AnimatedLoading()
        return
    }

    if (activeWalkCounter.value == false) {
        if (Build.VERSION_CODES.Q <= Build.VERSION.SDK_INT) {
            val parState =
                rememberPermissionState(permission = Manifest.permission.ACTIVITY_RECOGNITION)

            IntroScreen(
                onClose = onClose,
                onPositive = {
                    if (!parState.status.isGranted) {
                        if (parState.status is PermissionStatus.Denied) {
                            showDialog.value = true
                        } else {
                            parState.launchPermissionRequest()
                        }
                    } else {
                        activeWalkCounter.value = true
                    }
                }
            )
        } else {
            IntroScreen(
                onClose = onClose,
                onPositive = {
                    activeWalkCounter.value = true
                }
            )
        }
        return
    }

    WalkScreen(onClose = onClose)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun IntroScreen(
    onClose: () -> Unit = {},
    onPositive: () -> Unit = {}
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.stopService(Intent(context, WalkService::class.java))
    }

    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "",
                onClickBack = onClose,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = "만보기를 써보세요!",
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "앞으로의 걸음 수를 가져옵니다.",
                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
            )
            Text(
                text = "알림에서도 걸음 수를 확인할 수 있어요.",
                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
            )
            Spacer(modifier = Modifier.weight(2.0f))
            Icon(
                painter = painterResource(id = R.drawable.walking),
                contentDescription = "walking",
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.weight(3.0f))
            TextButton(
                onClick = onPositive,
                text = "만보기 시작하기",
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NoNotificationScreen(
    onClose: () -> Unit = {},
    onPositive: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "",
                onClickBack = onClose,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = "만보기를 알림 설정을 켜주세요!",
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "알림을 허용해야 돈을 받을 수 있어요.",
                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
            )
            Spacer(modifier = Modifier.weight(2.0f))
            Row(
                modifier = Modifier.padding(horizontal = 30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Disabled
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "만보기 알림",
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "소리 없음",
                        fontSize = dimensionResource(id = R.dimen.font_size_small).value.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Switch(checked = true, onCheckedChange = {})
            }
            Spacer(modifier = Modifier.weight(3.0f))
            TextButton(
                onClick = onPositive,
                text = "알림 켜기",
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }
}
