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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.UserBalanceInfo
import com.finance.android.ui.components.showHistoryList

@Composable
fun PointFragment(
//    attendanceViewModel: AttendanceViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    Screen(onClose)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
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
                balance = "100,000 포인트",
                type = "포인트",
            )
            showHistoryList(type = "포인트")
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