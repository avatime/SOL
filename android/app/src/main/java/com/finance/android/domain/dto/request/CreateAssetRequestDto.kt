package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class CreateAssetRequestDto(
    @SerializedName("phone")
    val phoneNumber: String
)
