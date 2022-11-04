package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CheckAccountResponseDto (
    @SerializedName("user_name")
    val userName : String,
)