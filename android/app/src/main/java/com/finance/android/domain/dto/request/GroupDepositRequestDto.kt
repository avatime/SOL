package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class GroupDepositRequestDto(
    @SerializedName("pa_id")
    val paId : Int,
    @SerializedName("value")
    val value : Int,
//    @SerializedName("ac_no")
//    val acNo : String, //보내는 계좌
)
