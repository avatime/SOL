package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class PointExchangeRequestDto(
    @SerializedName("account")
    val account : String,
    @SerializedName("point")
    val point : Int,
)
