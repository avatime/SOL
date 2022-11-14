package com.finance.backend.remit.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PhoneTokenRes(
        @JsonProperty("token_id")
        val tokenId : Long
)
