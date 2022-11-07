package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CardResponseDto(
    @SerializedName("card_info_res") // 카드 상세 정보
    val cardInfoRes: CardInfoResponseDto,
    @SerializedName("card_benefit_info_list") // 카드 혜택
    val cardBenefitInfoList: MutableList<CardBenefitInfoResponseDto>,
)
