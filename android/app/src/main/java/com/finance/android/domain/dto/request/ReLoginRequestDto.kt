package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class ReLoginRequestDto(
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String
)
