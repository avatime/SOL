package com.finance.android.domain.dto.response

data class LoginResponseDto(
    val userName: String,
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)
