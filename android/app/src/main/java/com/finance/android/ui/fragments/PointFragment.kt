package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.PointHistoryResponseDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.UserBalanceInfo
import com.finance.android.ui.components.showHistoryList
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.PointViewModel

@Composable
fun PointFragment(
    pointViewModel: PointViewModel = hiltViewModel(),
    navController: NavController,
    onClose: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        pointViewModel.launchPointHistory()
    }

    when(pointViewModel.getLoadState()) {
        is Response.Success -> Screen(
            onClose = onClose,
            navController = navController,
            pointHistoryList = (pointViewModel.pointHistoryList.value as Response.Success).data,
            userInfo = (pointViewModel.myInfo.value as Response.Success).data
        )
        is Response.Failure -> Loading("실패", onClose = onClose)
        else -> Loading(onClose = onClose)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    onClose: () -> Unit = {},
    navController: NavController,
    pointHistoryList : MutableList<PointHistoryResponseDto>,
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
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            UserBalanceInfo(
                title = "포인트",
                isAccount = false,
                balance = userInfo.point.toString() + " 포인트",
                type = "포인트",
                onClick = { navController.navigate(Const.Routes.EXCHANGE) }
            )
//            test2()
            showHistoryList(type = "포인트", historyList = List(pointHistoryList.size) {i -> pointHistoryList[i].toEntity()})
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