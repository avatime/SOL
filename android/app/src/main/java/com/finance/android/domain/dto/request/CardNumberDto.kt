package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class CardNumberDto(
    @SerializedName("cd_no")
    val cardNumber: String
)
