package com.finance.android.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.finance.android.services.WalkService
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.screens.more.ShowWalkingCalendar
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
                    onClose = onClose
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
    onClose: () -> Unit = {}
) {
    Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
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
                Text(text = "목표는 5000걸음", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Red)
        ) {
            Text(text = "현재 걸음 수 ", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(color = Color.Blue)
        ) {
            ShowWalkingCalendar(walkList)
        }
    }
}
