package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CardResponseDto(
    @SerializedName("cd_val_all") // 월 별 청구 금액
    val cardValueAll: Int,
    @SerializedName("card_info_res") // 카드 상세 정보
    val cardInfoRes: CardInfoResponseDto,
)
