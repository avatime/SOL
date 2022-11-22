package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class PushTokenRequestDto(
    @SerializedName("token")
    val token: String
)