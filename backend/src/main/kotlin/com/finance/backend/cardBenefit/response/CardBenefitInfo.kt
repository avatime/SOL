package com.finance.backend.cardBenefit.response

import com.fasterxml.jackson.annotation.JsonProperty

class CardBenefitInfo(
        cdBfImg : String,
        cdBfSum : String,
        cdBfName : String
) {
    @JsonProperty("cd_bf_img")
    val cdBfImg = cdBfImg

    @JsonProperty("cd_bf_sum")
    val cdBfSum = cdBfSum

    @JsonProperty("cd_bf_name")
    val cdBfName = cdBfName
}