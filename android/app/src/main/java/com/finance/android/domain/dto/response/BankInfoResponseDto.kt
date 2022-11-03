package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class BankInfoResponseDto(
    @SerializedName("cp_name")
    val cpName : String, //기업명
    @SerializedName("cp_logo")
    val cpLogo : String? //기업로고
)

