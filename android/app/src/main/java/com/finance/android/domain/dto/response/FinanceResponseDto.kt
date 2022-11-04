package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class FinanceResponseDto (
    @SerializedName("fn_name") // 기업 이름
    val fnName: String,
    @SerializedName("fn_logo") // 기업 로고
    val fnLogo: String,
    @SerializedName("fn_date") // 오늘 날짜
    val fnDate: Date,
    val close: Int, // 가격
    val per: Double // 등락율
)