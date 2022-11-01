package com.finance.backend.group.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class DuesRes(
        @JsonProperty("paid")
        val paid: Boolean,

        @JsonProperty("dues_name")
        val duesName : String,

        @JsonProperty("created_at")
        val createdAt : LocalDateTime,

        @JsonProperty("due_date")
        val dueDate : LocalDateTime?,

        @JsonProperty("dues_val")
        val duesVal : Long,

        @JsonProperty("paid_user")
        val paidUser : Int,

        @JsonProperty("total_user")
        val totalUser : Int,

        val creator : String
)
