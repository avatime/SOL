package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("username")
    val userName: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
