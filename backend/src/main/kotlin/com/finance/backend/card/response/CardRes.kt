package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.cardBenefit.response.CardBenefitInfo

class CardRes (
        cardInfoRes: CardInfoRes,
        cardBenefitInfoList: List<CardBenefitInfo>
) {
    @JsonProperty("card_info_res")
    val cardInfoRes = cardInfoRes

    @JsonProperty("card_benefit_info_list")
    val cardBenefitInfoList = cardBenefitInfoList
}