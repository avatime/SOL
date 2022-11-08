package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CardBillResponseDto(
    @SerializedName("cd_no") // 카드 번호
    val cardNo: String,
    @SerializedName("cd_name") // 카드 이름
    val cardName: String,
    @SerializedName("cd_img") // 카드 이미지
    val cardImage: String,
    @SerializedName("td_val") // 월 별 청구 금액
    val tradeValue: Int,
    @SerializedName("td_dt") // 결제일
    val tradeDate: Date
)