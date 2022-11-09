package com.finance.android.ui.screens

import android.Manifest
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.AccountRegisteredResponseDto
import com.finance.android.services.WalkService
import com.finance.android.ui.components.*
import com.finance.android.ui.theme.LightMainColor
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    fun launch() {
        homeViewModel.load()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                launch()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                homeViewModel = homeViewModel

            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            when (val data = homeViewModel.getLoadState()) {
                is Response.Success -> {
                    HomeCardContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10)
                            ),
                        navController = navController,
                        mainData = (homeViewModel.mainData.value as Response.Success).data
                    )
                    HomeCardContainer2(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10)
                            ),
                        navController = navController
                    )
                }
                is Response.Loading -> {}
                else -> {}
            }
        }
    }
}

@Composable
private fun HomeCardContainer(
    modifier: Modifier,
    navController: NavController,
    mainData: AccountRegisteredResponseDto
) {
    val totalSize =
        mainData.accountList.size + mainData.cardList.size + mainData.financeList.size + mainData.insuranceList.size

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "자산",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "$totalSize",
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = {
                    navController.navigate(Const.Routes.ASSET)
                },
                modifier = Modifier.size(30.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow_forward_ios),
                    contentDescription = "forwardArrow",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        mainData.accountList!!.forEach {
            AccountListItem_Remit(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                onClickItem = {
                    navController.navigate("${Const.Routes.ACC_DETAIL}/${it.acName}/${it.cpName}/${it.acNo}")
                },
                onClickRemit = {
                    navController.navigate("${Const.Routes.REMIT}/${it.cpName}/${it.acNo}/${it.balance}")
                }
            )
        }
        if (mainData.cardList.size != 0) {
            Divider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
        }
        mainData.cardList!!.forEach {
            val pathTmp = Uri.encode(it.cardImgPath)
            CardListItem_Arrow(
                cardName = it.cardName,
                cardImgPath = it.cardImgPath,
                onClickItem = {
                    navController.navigate("${Const.Routes.CARD_DETAIL}/${it.cardNumber}/$pathTmp/${it.cardName}")
                }
            )
        }
        if (mainData.insuranceList.size != 0) {
            Divider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
        }
        mainData.insuranceList!!.forEach {
            InsuranceListItem_Normal(
                insuranceName = it.isPdName,
                fee = it.isPdFee,
                myName = it.name,
                isName = it.isName
            )
        }
        if (mainData.financeList.size != 0) {
            Divider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
        }
        mainData.financeList!!.forEach {
            AccountListItem_Arrow(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                onClickItem = {
                    navController.navigate("${Const.Routes.ACC_DETAIL}/${it.acName}/${it.cpName}/${it.acNo}")
                }
            )
        }
        if (totalSize == 0) {
            Text(text = "자산 등록이 필요해요!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun HomeCardContainer2(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "쌀",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(30.dp)) {
                Image(
                    painter = painterResource(R.drawable.arrow_forward_ios),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ssal),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { /*TODO*/ },
                    text = "만보기",
                    modifier = Modifier
                        .height(40.dp)
                        .width(120.dp)
                        .padding(start = 20.dp),
                    buttonType = ButtonType.CIRCULAR,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1.0f))
                TextButton(
                    onClick = { /*TODO*/ },
                    text = "출석체크",
                    modifier = Modifier
                        .height(40.dp)
                        .width(120.dp)
                        .padding(end = 20.dp),
                    buttonType = ButtonType.CIRCULAR,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TopBar(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp,
                vertical = 10.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val onClick = { navController.navigate(Const.Routes.PEDOMETER) }

        if (Build.VERSION_CODES.Q <= Build.VERSION.SDK_INT) {
            val parState =
                rememberPermissionState(permission = Manifest.permission.ACTIVITY_RECOGNITION)
            if (!parState.status.isGranted) {
                PedometerOffStateButton(
                    onClick = onClick
                )
                return@Row
            }
        }

        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
            val manager =
                LocalContext.current.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            if (!manager.areNotificationsEnabled()) {
                PedometerOffStateButton(
                    onClick = onClick
                )
                return@Row
            }
        }

        PedometerOnStateButton(
            onClick = onClick,
            homeViewModel = homeViewModel
        )
    }
}

@Preview
@Composable
private fun PedometerOffStateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onClick()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_running_shoe),
            contentDescription = "running_shoe",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "걸음 보기"
        )
    }
}

@Preview
@Composable
private fun PedometerOnStateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    LaunchedEffect(Unit) {
        val serviceIntent = Intent(context, WalkService::class.java)
        ContextCompat.startForegroundService(context, serviceIntent)
    }

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    val sensorManager =
                        context.getSystemService(Service.SENSOR_SERVICE) as SensorManager
                    sensorManager.registerListener(
                        homeViewModel,
                        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                        SensorManager.SENSOR_DELAY_GAME
                    )
                }
                Lifecycle.Event.ON_PAUSE -> {
                    (context.getSystemService(Service.SENSOR_SERVICE) as SensorManager).unregisterListener(
                        homeViewModel
                    )
                }
                else -> {}
            }
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onClick()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        homeViewModel.walkCount.value?.let {
            Box(
                modifier = Modifier.size(24.dp)
            ) {
                CircularProgressIndicator(
                    progress = 1f,
                    color = LightMainColor
                )
                CircularProgressIndicator(
                    progress = it.toFloat() / Const.GOAL_WALK_COUNT,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = "$it 걸음"
            )
        }
    }
}
