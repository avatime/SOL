package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyWalkingResponseDto(
    @SerializedName("day")
    val day: Date,
    @SerializedName("success")
    val success: Boolean
)
