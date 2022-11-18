package com.finance.backend.group.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DuesPayReq(
        @JsonProperty("dues_id")
        val duesId : Long,
        @JsonProperty("dues_val")
        val duesVal : Long
        )
