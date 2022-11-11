package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.cardBenefit.CardBenefit
import com.finance.backend.cardBenefit.response.CardBenefitInfo

data class CardRecommendInfoRes(
    @JsonProperty("cd_pd_code")
    val cdPdCode : Long,

    @JsonProperty("cd_name")
    val cdName : String,

    @JsonProperty("cd_img")
    val cdImg : String,

    @JsonProperty("card_benefit_info_list")
    val cardBenefitInfoList : List<CardBenefitInfo>
)
