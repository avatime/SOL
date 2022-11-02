package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class AccountResponseDto(
    @SerializedName("ac_no")
    val acNo: String,
    val balance: Int,
    @SerializedName("ac_name")
    val acName: String,
    @SerializedName("cp_name")
    val cpName: String,
    @SerializedName("cp_logo")
    val cpLogo: String
)
