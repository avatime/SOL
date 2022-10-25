package com.finance.backend.group.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class PublicTradeRes(
        @JsonProperty("td_dt")
        val tdDt : LocalDateTime,
        @JsonProperty("td_val")
        val tdVal : Int,
        @JsonProperty("dues_name")
        val duesName : String,
        @JsonProperty("user_name")
        val userName : String,
        @JsonProperty("td_type")
        val tdType : String
)
