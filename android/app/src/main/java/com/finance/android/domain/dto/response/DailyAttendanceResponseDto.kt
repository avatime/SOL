package com.finance.android.domain.dto.response

import java.util.Date

data class DailyAttendanceResponseDto(
    val day: Date,
    val attendance: Boolean
)
