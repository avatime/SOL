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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.BaseScreen
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.screens.more.ShowAttendanceCalendar
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.AttendanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceFragment(
    attendanceViewModel: AttendanceViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        attendanceViewModel.launchAttendance()
    }

    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "출석체크",
                onClickBack = onClose
            )
        }
    ) {
        BaseScreen(
            loading = attendanceViewModel.loading.value,
            error = attendanceViewModel.error.value,
            onError = { attendanceViewModel.launchAttendance() },
            calculatedTopPadding = it.calculateTopPadding()
        ) {
            if (attendanceViewModel.attendanceList.value.isNotEmpty()) {
                Screen(
                    onClickIsAttend = { attendanceViewModel.onClickIsAttend() },
                    attendanceList = attendanceViewModel.attendanceList.value,
                    isAttend = attendanceViewModel.isAttend.value,
                    onClose = onClose
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    onClickIsAttend: () -> Unit,
    attendanceList: Array<DailyAttendanceResponseDto>,
    isAttend: Boolean,
    onClose: () -> Unit = {}
) {
    Column(
//            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background)
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(text = "매일 출석체크하고", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        Text(text = "쏠포인트를 모아요", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))

        ShowAttendanceCalendar(attendanceList)

        TextButton(
            onClick = { onClickIsAttend() },
            modifier = Modifier.withBottomButton(),
            enabled = !isAttend,
            text = if (!isAttend) "출석하고 50 포인트 받기" else "출석완료",
            buttonType = ButtonType.ROUNDED
        )
    }
}
