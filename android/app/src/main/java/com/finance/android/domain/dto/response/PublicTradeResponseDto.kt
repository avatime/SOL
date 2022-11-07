package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class PublicTradeResponseDto(
    @SerializedName("td_dt")
    val tdDt : String, //거래 일자
    @SerializedName("td_val")
    val tdVal : Int,//거래 금액
    @SerializedName("dues_name")
    val duesName: String,//회비 이름
    @SerializedName("user_name")
    val userName : String,//거래한 사람 이름
    @SerializedName("td_type")
    val tdType : String, //입금유형
)
