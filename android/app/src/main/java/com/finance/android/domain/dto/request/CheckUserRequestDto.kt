package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class CheckUserRequestDto(
    @SerializedName("username")
    val userName: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("birth")
    val birthday: String
)