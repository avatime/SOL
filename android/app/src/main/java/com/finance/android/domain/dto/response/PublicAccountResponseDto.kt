package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class PublicAccountResponseDto(
    @SerializedName("pa_id")
    val paId : Int,//모임 Id
    @SerializedName("pa_name")
    val paName : String,// 모임명
    @SerializedName("amount")
    val amount : Int, //금액
)
