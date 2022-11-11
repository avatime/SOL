package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class PointExchangeRequestDto(
    @SerializedName("point")
    val point : Int,
)
