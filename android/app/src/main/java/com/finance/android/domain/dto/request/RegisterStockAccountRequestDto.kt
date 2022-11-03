package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class RegisterStockAccountRequestDto(
    @SerializedName("ac_no")
    val accountNumber: String
)