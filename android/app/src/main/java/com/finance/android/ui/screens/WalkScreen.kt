package com.finance.android.ui.screens

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
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
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.finance.android.services.WalkService
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.screens.more.ShowWalkingCalendar
import com.finance.android.ui.theme.LightMainColor
import com.finance.android.utils.Response
import com.finance.android.viewmodels.WalkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkScreen(
    walkViewModel: WalkViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val serviceIntent = Intent(context, WalkService::class.java)
        ContextCompat.startForegroundService(context, serviceIntent)
        walkViewModel.launchAttendance()
    }

    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    val sensorManager =
                        context.getSystemService(Service.SENSOR_SERVICE) as SensorManager
                    sensorManager.registerListener(
                        walkViewModel,
                        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                        SensorManager.SENSOR_DELAY_GAME
                    )
                }
                Lifecycle.Event.ON_PAUSE -> {
                    (context.getSystemService(Service.SENSOR_SERVICE) as SensorManager).unregisterListener(
                        walkViewModel
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

    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "만보기",
                backgroundColor = MaterialTheme.colorScheme.surface,
                onClickBack = onClose
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            when (walkViewModel.getLoadState()) {
                is Response.Success -> Screen(
                    walkList = (walkViewModel.walkingList.value as Response.Success).data,
                    walkCount = walkViewModel.walkCount.value
                )
                is Response.Failure -> Text("실패")
                else -> AnimatedLoading()
            }
        }
    }
}

@Composable
private fun Screen(
    walkList: MutableList<DailyWalkingResponseDto>,
    walkCount: Int?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = "오늘의 미션 도전 중",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Blue
                )
                Text(
                    text = "목표는 5,000걸음",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Column(
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.paw),
                    contentDescription = null
                )
            }
        }

        DrawProgress(walkCount = walkCount)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            ShowWalkingCalendar(walkList)
        }
    }
}

@Preview
@Composable
private fun DrawProgress(walkCount: Int? = 2500) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        walkCount?.let {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val maxWidth = rememberUpdatedState(maxWidth)
                val percentage = maxWidth.value * it / 5000
                ConstraintLayout(modifier = Modifier.height(120.dp)) {
                    val (count, progress, shoe, ssal) = createRefs()
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(5.dp)
                            .constrainAs(count) {
                                top.linkTo(parent.top)
                                start.linkTo(
                                    parent.start,
                                    margin = min(maxWidth.value * 0.9f, percentage)
                                )
                            }
                    ) {
                        Text(
                            text = it.toString(),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .constrainAs(progress) {
                                bottom.linkTo(shoe.top, margin = 10.dp)
                                bottom.linkTo(ssal.top, margin = 10.dp)
                            }
                    ) {
                        if (20.dp < percentage) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(LightMainColor)
                                    .fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .fillMaxHeight()
                                    .width(percentage)
                            )
                        } else {
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .fillMaxSize(),
                                progress = it.toFloat() / 5000,
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = LightMainColor
                            )
                        }
                    }

                    Icon(
                        modifier = Modifier
                            .size(45.dp)
                            .constrainAs(shoe) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            },
                        painter = painterResource(id = R.drawable.ic_running_shoe),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                    Image(
                        modifier = Modifier
                            .size(45.dp)
                            .constrainAs(ssal) {
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            },
                        painter = painterResource(id = R.drawable.ssal),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}
