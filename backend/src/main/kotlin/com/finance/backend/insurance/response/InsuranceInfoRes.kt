package com.finance.backend.insurance.response

import com.fasterxml.jackson.annotation.JsonProperty

data class InsuranceInfoRes(
        @JsonProperty("is_id")
        val isId : Long,

        @JsonProperty("is_pd_name")
        val isPdName : String,

        @JsonProperty("is_pd_fee")
        val isPdFee : Int,

        @JsonProperty("name")
        val name : String,

        // 피보험자
        @JsonProperty("is_name")
        val isName : String
)
