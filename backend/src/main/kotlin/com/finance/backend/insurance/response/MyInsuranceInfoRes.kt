package com.finance.backend.insurance.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MyInsuranceInfoRes(
        @JsonProperty("total_fee")
        val totalFee : Int,

        val list: List<MyInsuranceInfoDetailRes>
)
