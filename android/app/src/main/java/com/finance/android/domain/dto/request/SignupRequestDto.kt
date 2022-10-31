package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class SignupRequestDto(
    @SerializedName("username")
    val userName: String,
    @SerializedName("phone")
    val phoneNumber: String,
    val password: String,
    @SerializedName("birth")
    val birthday: String,
    val sex: Int,
    val type: String = "회원"
)