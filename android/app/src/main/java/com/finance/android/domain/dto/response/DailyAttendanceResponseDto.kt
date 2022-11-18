package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyAttendanceResponseDto(
    @SerializedName("day")
    val day: Date,
    @SerializedName("attendance")
    val attendance: Boolean
)
