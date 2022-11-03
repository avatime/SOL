package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class MyInsuranceInfoResponseDto(
    @SerializedName("total_fee")
    val totalFee: Int,
    val list: MutableList<InsuranceInfoResponseDto>
)