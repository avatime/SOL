package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.R
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.screens.point.InputExchangePoint
import com.finance.android.utils.Response
import com.finance.android.viewmodels.PointViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                else -> SuccessScreen(pointViewModel, onClose = onClose)
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
                onClickBack = onClose
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            InputExchangePoint(userInfo.point)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessScreen(
    pointViewModel: PointViewModel,
    onClose: () -> Unit = {},
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
            Text(text = if(pointViewModel.success.value == 1) "성공" else "실패")
            Button(onClick = {
                if(pointViewModel.success.value == 1) onClose
                else pointViewModel.success.value = 0
            }) {
                Text(text = "확인")
            }
        }
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