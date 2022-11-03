package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRes(
        @JsonProperty("user_name")
        val userName : String
)
