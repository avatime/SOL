package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class FinanceDetailResponseDto(
    @SerializedName("fn_name") // 기업 이름
    val fnName: String,
    @SerializedName("fn_date") // 일주일 날짜
    val fnDate: Date,
    @SerializedName("open")
    val open: Int, // 시가
    @SerializedName("close")
    val close: Int, // 종가
    @SerializedName("high")
    val high: Int, // 최고
    @SerializedName("low")
    val low: Int, // 최저
    @SerializedName("volume")
    val volume: Int, // 거래량
    @SerializedName("per")
    val per: Double // 등락율
)
