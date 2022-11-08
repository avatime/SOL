package com.finance.android.ui.screens.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun ShowAttendanceCalendar(
    attendanceList: MutableList<DailyAttendanceResponseDto>
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(0) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(0) } // Adjust as needed
    val daysOfWeek = daysOfWeek()
    val attendanceNum : Int = attendanceList.filter { i -> i.attendance }.size

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
            Text(
                text = state.firstVisibleMonth.yearMonth.monthValue.toString() + "월",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
            Text(text = "이번 달 출석한 횟수", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
            Text(text = attendanceNum.toString(), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))
            HorizontalCalendar(
                state = state,
                dayContent = { Day(it, attendanceList) }
            )
        }
    }
}

@Composable
fun ShowWalkingCalendar(
    attendanceList: MutableList<DailyWalkingResponseDto>
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(0) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(0) } // Adjust as needed
    val daysOfWeek = daysOfWeek()
    val attendanceNum : Int = attendanceList.filter { i -> i.success }.size

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
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "미션 달성 기록", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
            Text(
                text = state.firstVisibleMonth.yearMonth.year.toString() + "년 " + state.firstVisibleMonth.yearMonth.monthValue.toString() + "월",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))
            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.calendar_default)))
            HorizontalCalendar(
                state = state,
                dayContent = { WalkDay(it, attendanceList) }
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
fun Day(day: CalendarDay, attendanceList : MutableList<DailyAttendanceResponseDto>) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        if (day.position == DayPosition.MonthDate && day.date.month == LocalDate.now().month && day.date.year == LocalDate.now().year && attendanceList[day.date.dayOfMonth - 1].attendance) {
            Image(
                painter = painterResource(R.drawable.paw),
                contentDescription = null, // 필수 param
            )
        } else {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) Color.Black else Color.White // Color.White
            )
        }
    }
}

@Composable
fun WalkDay(day: CalendarDay, attendanceList : MutableList<DailyWalkingResponseDto>) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        if (day.position == DayPosition.MonthDate && day.date.month == LocalDate.now().month && day.date.year == LocalDate.now().year && attendanceList[day.date.dayOfMonth - 1].success) {
            Image(
                painter = painterResource(R.drawable.paw),
                contentDescription = null, // 필수 param
            )
        } else {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray // Color.White
            )
        }
    }
}