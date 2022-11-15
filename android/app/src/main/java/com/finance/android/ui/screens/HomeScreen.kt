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
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import coil.compose.AsyncImage
import com.finance.android.R
import com.finance.android.domain.dto.response.AccountRegisteredResponseDto
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.services.WalkService
import com.finance.android.ui.components.*
import com.finance.android.ui.theme.LightMainColor
import com.finance.android.ui.theme.SetStatusBarColor
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.net.URLEncoder
import java.text.DecimalFormat
import kotlin.math.roundToInt

@ExperimentalAnimationApi
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

    SetStatusBarColor(color = MaterialTheme.colorScheme.background)
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                homeViewModel = homeViewModel
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        BaseScreen(
            loading = homeViewModel.loading.value,
            error = homeViewModel.error.value,
            onError = { homeViewModel.load() },
            calculatedTopPadding = it.calculateTopPadding()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                if (homeViewModel.mainData.value != null) {
                    HomeCardContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        navController = navController,
                        mainData = homeViewModel.mainData.value!!
                    )
                }
                HomeCardContainer2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    navController = navController
                )
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
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
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
        mainData.accountList.forEach {
            val pathTmp = Uri.encode(it.cpLogo)
            AccountListItem_Remit(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                companyName = it.cpName,
                acMain = it.acMain,
                onClickItem = {
                    navController.navigate("${Const.Routes.ACC_DETAIL}/${it.acName}/${it.cpName}/${it.acNo}/$pathTmp/${it.acMain}/${it.acType}")
                },
                onClickRemit = {
                    navController.navigate("${Const.Routes.REMIT}/${it.cpName}/${it.acNo}/${it.balance}")
                }
            )
        }
        if (mainData.cardList.size != 0) {
            Divider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
        }
        mainData.cardList.forEach {
            val pathTmp = Uri.encode(it.cardInfoRes.cardImgPath)
            CardListItem_Arrow(
                cardName = it.cardInfoRes.cardName,
                cardImgPath = it.cardInfoRes.cardImgPath,
                cardFee = "당월 청구 금액 : " + DecimalFormat("#,###원").format(it.cardValueAll),
                onClickItem = {
                    navController.navigate("${Const.Routes.CARD_DETAIL}/${it.cardInfoRes.cardName}/${it.cardInfoRes.cardNumber}/$pathTmp/${it.cardValueAll}")
                }
            )
        }
        if (mainData.insuranceList.size != 0) {
            Divider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))
        }
        mainData.insuranceList.forEach {
            InsuranceListItem_Arrow(
                insuranceName = it.isPdName,
                fee = it.isPdFee,
                myName = it.name,
                isName = it.isName,
                onClickItem = {
                    navController.navigate("${Const.Routes.INSURANCE}/${it.isPdId}/${it.isPdName}")
                }
            )
        }
        if (mainData.financeList.size != 0) {
            Divider(
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
            )
        }
        mainData.financeList.forEach {
            AccountListItem_Arrow(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                companyName = it.cpName,
                acMain = it.acMain,
                onClickItem = {
                    navController.navigate(
                        "${Const.Routes.ACC_DETAIL}/${it.acName}/${it.cpName}/${it.acNo}/${
                        URLEncoder.encode(
                            it.cpLogo
                        )
                        }/${it.acMain}/${it.acType}"
                    )
                }
            )
        }
        if (totalSize == 0) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "자산 등록이 필요해요!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 80.dp)
                )
                TextButton(
                    onClick = { navController.navigate(Const.Routes.ADD_ASSET) },
                    text = "자산 등록하기",
                    buttonType = ButtonType.ROUNDED,
                    modifier = Modifier.withBottomButton()
                )
            }
        }
    }
}

@Composable
private fun HomeCardContainer2(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "쌀",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = { navController.navigate(Const.Routes.POINT) },
                modifier = Modifier.size(30.dp)
            ) {
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
                    onClick = { navController.navigate(Const.Routes.PEDOMETER) },
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
                    onClick = { navController.navigate(Const.Routes.ATTENDANCE) },
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

@ExperimentalAnimationApi
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TopBar(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Row(
        modifier = Modifier
            .padding(
                vertical = 10.dp,
                horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val onClick = { navController.navigate(Const.Routes.PEDOMETER) }
        var flag = false

        if (Build.VERSION_CODES.Q <= Build.VERSION.SDK_INT) {
            val parState =
                rememberPermissionState(permission = Manifest.permission.ACTIVITY_RECOGNITION)
            if (!parState.status.isGranted) {
                PedometerOffStateButton(
                    onClick = onClick
                )
                flag = true
            }
        }

        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
            val manager =
                LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!manager.areNotificationsEnabled() && !flag) {
                PedometerOffStateButton(
                    onClick = onClick
                )
                flag = true
            }
        }

        if (!flag) {
            PedometerOnStateButton(
                onClick = onClick,
                homeViewModel = homeViewModel
            )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    onClick()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (homeViewModel.stockList.value.isNotEmpty()) animate_num(
                homeViewModel,
                navController = navController
            )
        }
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

@ExperimentalAnimationApi
@Composable
fun animate_num(homeViewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    var state by remember { mutableStateOf(0) }
    var count by remember { mutableStateOf(0) }
    val transition = rememberInfiniteTransition()
    val value by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = { it.roundToInt().toFloat() }),
            repeatMode = RepeatMode.Reverse
        )
    )
    if (state != value.toInt()) {
        state = value.toInt()
        if (state == 0) count++
    }

    AnimatedContent(
        targetState = count,
        transitionSpec = {
            // Compare the incoming number with the previous number.
            if (targetState > initialState) {
                // If the target number is larger, it slides up and fades in
                // while the initial (smaller) number slides up and fades out.
                slideInVertically { height -> height } + fadeIn() with
                    slideOutVertically { height -> -height } + fadeOut()
            } else {
                // If the target number is smaller, it slides down and fades in
                // while the initial number slides down and fades out.
                slideInVertically { height -> -height } + fadeIn() with
                    slideOutVertically { height -> height } + fadeOut()
            }.using(
                // Disable clipping since the faded slide-in/out should
                // be displayed out of bounds.
                SizeTransform(clip = false)
            )
        }
    ) { targetCount ->
        minibar(
            idx = targetCount,
            stockList = homeViewModel.stockList.value,
            navController = navController
        )
    }
}

@Composable
fun minibar(
    idx: Int = 0,
    modifier: Modifier = Modifier,
    navController: NavController,
    stockList: Array<FinanceResponseDto>
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                navController.navigate("${Const.Routes.STOCK}/${stockList[idx % stockList.size].fnName}")
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val stock = stockList[idx % stockList.size]
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(24.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.clip(CircleShape).background(color = Color.White),
                    model = stock.fnLogo,
                    contentDescription = stock.fnName
                )
            }
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = stock.fnName,
                fontSize = if (stock.fnName.length > 7) 12.sp else 16.sp
            )
        }
//        Spacer(modifier = Modifier.width(7.dp))
        Spacer(modifier = Modifier.weight(1.0f))
        val per = stock.per
        val color = if (per > 0) Color.Red else Color.Blue
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = DecimalFormat("#,###원").format(stock.close),
                color = color,
                fontSize = 12.sp
            )
            Text(
                text = "${if (per > 0) "+" else ""}$per%",
                color = color,
                fontSize = 9.sp
            )
        }
    }
}
