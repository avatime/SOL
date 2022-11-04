package com.finance.android.domain.dto.response

import java.util.Date

data class DailyWalkingResponseDto(
    val day: Date,
    val success: Boolean,
    val walk : Int
)
