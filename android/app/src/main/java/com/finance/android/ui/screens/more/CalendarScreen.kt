package com.finance.android.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.utils.ext.withBottomButton
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun CalendarScreen() {
    Scaffold(
        topBar = { BackHeaderBar(text = "출석체크", modifier = Modifier) }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
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

            ShowCalendar()

            Button(
                onClick = { },
                modifier = Modifier.withBottomButton()
            ) {
                Text("절대 누르지 마시오")
            }
        }
    }
}

@Preview
@Composable
fun ShowCalendar() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val daysOfWeek = daysOfWeek()

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    BoxWithConstraints(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(dimensionResource(R.dimen.calendar_default))
            )
            .padding(dimensionResource(R.dimen.calendar_default))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = currentMonth.monthValue.toString() + "월", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
            Text(text = "이번 달 출석한 횟수", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
            Text(text = "N", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))
            HorizontalCalendar(
                state = state,
                dayContent = { Day(it) }
            )
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun Day(day: CalendarDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        if(day.position == DayPosition.MonthDate && day.date.dayOfMonth % 2 == 0){
            Image(
                painter = painterResource(R.drawable.paw),
                contentDescription = null, // 필수 param
            )
        }
        else{
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) Color.Black else Color.White
            )
        }
    }
}