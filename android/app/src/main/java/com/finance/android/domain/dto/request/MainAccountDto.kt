package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class MainAccountDto (
        @SerializedName("ac_no")
        val acNo: String
)