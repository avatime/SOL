package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyProfileResponseDto(
    @SerializedName("profile_id")
    val profileId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
