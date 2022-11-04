package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class BankInfoResponseDto(
//    @SerializedName("cp_code")
//    var cpCode : Int,
    @SerializedName("cp_name")
    var cpName : String, //기업명
    @SerializedName("cp_logo")
    var cpLogo : String //기업로고
)

