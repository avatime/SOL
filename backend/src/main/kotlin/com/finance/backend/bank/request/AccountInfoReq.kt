package com.finance.backend.bank.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountInfoReq (
        @JsonProperty("ac_no")
        val acNo: String
)