package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CardRecommendInfoResponseDto (
    @SerializedName("cd_pd_code") // 카드 상품 번호
    val cardPdCode: Int,
    @SerializedName("cd_name") // 카드 이름
    val cardName: String,
    @SerializedName("cd_img") // 카드 이미지
    val cardImage: String,
    @SerializedName("cd_bf_img") // 카드 혜택 이미지
    val cardBenefitImage: String,
    @SerializedName("cd_bf_sum") // 카드 혜택 요약
    val cardBenefitSummary: String,
    @SerializedName("cd_bf_name") // 카드 혜택 이름
    val cardBenefitName: String,
)