package com.finance.backend.group.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.temporal.TemporalAmount

data class PublicAccountRes(
        @JsonProperty("pa_id")
        val paId : Long,
        @JsonProperty("pa_name")
        val paName : String,
        val amount: Long
)
