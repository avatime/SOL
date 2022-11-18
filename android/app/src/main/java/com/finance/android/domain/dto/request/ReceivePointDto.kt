package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class ReceivePointDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("point")
    val point: Int
)