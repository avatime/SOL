package com.finance.backend.remit.response

import com.fasterxml.jackson.annotation.JsonProperty

data class RemitAvailableRes(
        @JsonProperty("token_id")
        val tokenId : Long,

        val token : Boolean
)