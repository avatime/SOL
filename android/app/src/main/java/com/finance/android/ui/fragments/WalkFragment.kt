package com.finance.android.ui.screens

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.screens.more.ShowAttendanceCalendar
import com.finance.android.ui.screens.more.ShowWalkingCalendar
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.AttendanceViewModel
import com.finance.android.viewmodels.WalkViewModel
import java.util.*

@Composable
fun WalkFragment(
    walkViewModel: WalkViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        walkViewModel.launchAttendance()
    }

    when(walkViewModel.getLoadState()) {
        is Response.Success -> Screen(
            walkLikst = (walkViewModel.walkingList.value as Response.Success).data,
            onClose = onClose
        )
        is Response.Failure -> Loading("실패", onClose = onClose)
        else -> Loading(onClose = onClose)
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
                text = "만보기",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    walkLikst : MutableList<DailyWalkingResponseDto>,
    onClose: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "만보기",
                onClickBack = onClose
            )
        }
    ) {
        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)
            ){
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
                    Text(text = "목표는 ${Const.GOAL_WALK_COUNT}걸음", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                }
               Column(
                   modifier = Modifier
                       .height(70.dp)
                       .width(70.dp)
               ) {
                   Image(painter = painterResource(R.drawable.paw), contentDescription = null, contentScale = ContentScale.Fit)
               }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Red)
            ) {
                Text(text = "현재 걸음 수 ", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(color = Color.Blue)
            ) {
                ShowWalkingCalendar(walkLikst)
            }
        }
    }
}