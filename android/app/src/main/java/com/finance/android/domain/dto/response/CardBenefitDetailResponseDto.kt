package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CardBenefitDetailResponseDto(
    @SerializedName("cd_bf_name") // 카드 혜택 이름
    val cardBenefitName: String,
    @SerializedName("cd_bf_sum") // 카드 혜택 요약
    val cardBenefitSummary: String,
    @SerializedName("cd_bf_img") // 카드 혜택 경로
    val cardBenefitImage: String,
    @SerializedName("cd_bf_detail") // 카드 혜택 상세
    val cardBenefitDetail: String
)