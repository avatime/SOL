package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class SignupRequestDto(
    @SerializedName("username")
    val userName: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("birth")
    val birthday: String,
    @SerializedName("sex")
    val sex: Int,
    @SerializedName("type")
    val type: String = "회원"
)