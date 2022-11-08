package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CardBillDetailResponseDto(
    @SerializedName("cd_py_dt") // 카드 사용 일자
    val cardPayDate: Date,
    @SerializedName("cd_py_name") // 카드 결제 상호명
    val cardPayName: String,
    @SerializedName("cd_val") // 결제 금액
    val cardValue: Int,
    @SerializedName("cd_tp") // 결제 타입 (1: 일시불, 2: 할부)
    val cardType: Int



        )