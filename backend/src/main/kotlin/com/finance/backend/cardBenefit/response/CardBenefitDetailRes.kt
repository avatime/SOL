package com.finance.backend.cardBenefit.response

import com.fasterxml.jackson.annotation.JsonProperty

class CardBenefitDetailRes (
        cdBfName: String,
        cdBfSum: String,
        cdBfImg: String,
        cdBfDetail: String
) {

    @JsonProperty("cd_bf_name")
    val cdBfName: String = cdBfName

    @JsonProperty("cd_bf_sum")
    val cdBfSum: String = cdBfSum

    @JsonProperty("cd_bf_img")
    val cdBfImg: String = cdBfImg

    @JsonProperty("cd_bf_detail")
    val cdBfDetail: String = cdBfDetail
}