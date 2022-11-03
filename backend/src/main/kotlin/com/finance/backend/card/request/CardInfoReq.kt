package com.finance.backend.card.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CardInfoReq(
        @JsonProperty("cd_no")
        val cdNo: String
)
