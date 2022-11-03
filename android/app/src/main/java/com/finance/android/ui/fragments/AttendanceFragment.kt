package com.finance.android.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.screens.more.ShowCalendar
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.AttendanceViewModel
import java.util.*

@Composable
fun AttendanceFragment(
    attendanceViewModel: AttendanceViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        attendanceViewModel.launchAttendance()
    }

    when(attendanceViewModel.getLoadState()) {
        is Response.Success -> Screen(
            onClickIsAttend = { attendanceViewModel.onClickIsAttend() },
            attendanceList = (attendanceViewModel.attendanceList.value as Response.Success).data,
            isAttend = attendanceViewModel.isAttend.value,
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
                text = "출석체크",
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
    onClickIsAttend: () -> Unit,
    attendanceList : MutableList<DailyAttendanceResponseDto>,
    isAttend : Boolean,
    onClose: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "출석체크",
                onClickBack = onClose
            )
        }
    ) {
        Column(
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {

            Text(text = "매일 출석체크하고", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = "쏠포인트를 모아요", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))

            ShowCalendar(attendanceList)

            TextButton(
                onClick = { onClickIsAttend() },
                modifier = Modifier.withBottomButton(),
                enabled = !isAttend,
                text = if(!isAttend) "절대 누르지 마시오" else "출석완료",
                buttonType = ButtonType.ROUNDED
            )
        }
    }
}