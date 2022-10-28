package com.finance.backend.insurance.response

import com.fasterxml.jackson.annotation.JsonProperty

data class InsuranceProductInfoDetailRes(
        val id : Long,
        val name : String,
        val type : String,
        val detail : List<String>?
)
