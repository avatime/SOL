package com.finance.backend.remit.response

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column

data class RemitTokenRes(
        @JsonProperty("token")
        val token : Boolean,

        @JsonProperty("ac_name")
        var acName : String,

        @JsonProperty("ac_send")
        var acSend : String,

        @JsonProperty("sender")
        var sender : String,

        @JsonProperty("value")
        var value : Long,
)
