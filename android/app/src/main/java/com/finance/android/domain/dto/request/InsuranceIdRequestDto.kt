package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class InsuranceIdRequestDto(
    @SerializedName("is_id")
    val insuranceId: Long
)