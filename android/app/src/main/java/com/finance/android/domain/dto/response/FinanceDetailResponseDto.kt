package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class FinanceDetailResponseDto(
    @SerializedName("fn_name") // 기업 이름
    val fnName: String,
    @SerializedName("fn_date") // 일주일 날짜
    val fnDate: Date,
    val open: Int, // 시가
    val close: Int, // 종가
    val high: Int, // 최고
    val low: Int, // 최저
    val volume: Int, // 거래량
    val per: Double // 등락율
)
