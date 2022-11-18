package com.finance.backend.insurance.response

import com.fasterxml.jackson.annotation.JsonProperty

data class InsuranceProductInfoRes(
        val id : Long,
        val name : String,
        val type : String
)
