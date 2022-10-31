package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("refresh_token")
    val refreshToken: String,
    val password: String
)