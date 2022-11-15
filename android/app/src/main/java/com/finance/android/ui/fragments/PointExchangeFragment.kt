package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.ButtonColor
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.screens.point.InputExchangePoint
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.PointViewModel

@Composable
fun PointExchangeFragment(
    pointViewModel: PointViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        pointViewModel.launchPointExchange()
    }

    when(pointViewModel.getLoadStateExchange()) {
        is Response.Success -> {
            when(pointViewModel.success.value) {
                0 -> Screen(
                onClose = onClose,
                pointViewModel = pointViewModel,
                userInfo = (pointViewModel.myInfo.value as Response.Success).data
                )
                1 -> SuccessScreen(pointViewModel, onClose = onClose)
                else -> FailureScreen(onClose = onClose)
            }
        }
        is Response.Failure -> Loading("실패", onClose = onClose)
        else -> Loading(onClose = onClose)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    onClose: () -> Unit = {},
    pointViewModel: PointViewModel,
    userInfo : UserProfileResponseDto
) {
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "포인트",
                onClickBack = onClose,
                backgroundColor = MaterialTheme.colorScheme.background
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
//                .padding(start = dimensionResource(R.dimen.padding_medium))
        ) {
            InputExchangePoint(
                balance = userInfo.point,
                pointViewModel = pointViewModel
            )
        }
    }
}

@Composable
private fun SuccessScreen(
    pointViewModel: PointViewModel,
    onClose: () -> Unit = {},
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_done))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "**"
            )
        )
    )
    Column(  modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(20.dp))
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(100.dp),
            dynamicProperties = dynamicProperties
        )
        Spacer(modifier = Modifier.padding(10.dp))

        androidx.compose.material.Text(
            text = "${pointViewModel.exchangedPoint.value} 포인트",
            fontSize = 25.sp,
        )
        androidx.compose.material.Text(text = "전환완료", fontSize = 25.sp)
        Spacer(modifier = Modifier.weight(1f))
        com.finance.android.ui.components.TextButton(
            onClick = onClose,
            text = "완료",
            modifier = Modifier.withBottomButton(),
            buttonType = ButtonType.ROUNDED,

            )
    }
}

@Composable
private fun FailureScreen(
    onClose: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_failed))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    Column(  modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(20.dp))
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(100.dp),
        )
        Spacer(modifier = Modifier.padding(10.dp))

        androidx.compose.material.Text(
            text = "포인트 전환 실패",
            fontSize = 25.sp,
        )
        androidx.compose.material.Text(text = "문제가 계속될시 문의주세요", fontSize = 25.sp)
        Spacer(modifier = Modifier.weight(1f))
        com.finance.android.ui.components.TextButton(
            onClick = onClose,
            buttonColor = ButtonColor.ERROR,
            text = "확인",
            modifier = Modifier.withBottomButton(),
            buttonType = ButtonType.ROUNDED,
            border = false
            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Loading(
    message : String = "로딩 중...",
    onClose: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "포인트",
                onClickBack = onClose
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(text = message, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}