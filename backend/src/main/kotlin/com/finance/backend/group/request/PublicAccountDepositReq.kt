package com.finance.backend.group.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PublicAccountDepositReq(
        @JsonProperty("pa_id")
        val publicAccountId : Long,
        @JsonProperty("value")
        val value : Long,
        )
