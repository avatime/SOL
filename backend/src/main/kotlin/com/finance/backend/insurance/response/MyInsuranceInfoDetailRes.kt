package com.finance.backend.insurance.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MyInsuranceInfoDetailRes(
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
        val insName : String,

        @JsonProperty("is_reg")
        val isReg : Boolean,

        @JsonProperty("is_pd_id")
        val isPdId : Long,
)
