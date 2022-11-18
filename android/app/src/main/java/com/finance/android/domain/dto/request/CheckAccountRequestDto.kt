package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class CheckAccountRequestDto (
    @SerializedName("ac_no")
    val acNo : String,
    @SerializedName("cp_code")
    val cpCode : Int,
)