package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class ChangeProfileRequestDto(
    @SerializedName("profile_no")
    val profileNo : Int
)
