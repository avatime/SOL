package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.cardBenefit.response.CardBenefitInfo

class CardRes (
        cdImg: String,
        cdName: String,
        cardBenefitInfoList: List<CardBenefitInfo>
) {
    @JsonProperty("cd_img")
    val cdImg: String = cdImg

    @JsonProperty("cd_name")
    val cdName: String = cdName

    @JsonProperty("card_benefit_info_list")
    val cardBenefitInfoList = cardBenefitInfoList
}