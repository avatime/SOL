package com.finance.backend.cardBenefit.response

import com.fasterxml.jackson.annotation.JsonProperty

class CardBenefitRes(
        cpName: String,
        cdBfName: String,
        cdBfSum: String,
        cdBfImg: String
) {
    @JsonProperty("cp_name")
    val cpName: String = cpName

    @JsonProperty("cd_bf_name")
    val cdBfName: String = cdBfName

    @JsonProperty("cd_bf_sum")
    val cdBfSum: String = cdBfSum

    @JsonProperty("cd_bf_img")
    val cdBfImg: String = cdBfImg
}