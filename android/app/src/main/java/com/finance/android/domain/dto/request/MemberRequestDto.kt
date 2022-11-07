package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class MemberRequestDto(
    @SerializedName("name")
    val name : String,

    @SerializedName("phone")
    val phone : String
)
