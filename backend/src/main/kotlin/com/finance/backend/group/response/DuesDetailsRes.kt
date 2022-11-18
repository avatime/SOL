package com.finance.backend.group.response

import com.fasterxml.jackson.annotation.JsonProperty

data class DuesDetailsRes(
        @JsonProperty("dues_name")
        val duesName : String,

        @JsonProperty("dues_val")
        val duesVal : Long,

        val check : List<IsPaidRes>,

        @JsonProperty("can_delete")
        val canDelete : Boolean
)
