package com.finance.backend.group.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class DuesRes(
        @JsonProperty("dues_name")
        val duesName : String,

        @JsonProperty("created_at")
        val created_at : LocalDateTime,

        @JsonProperty("dues_val")
        val duesVal : Int,

        @JsonProperty("paid_user")
        val paidUser : Int,
)
