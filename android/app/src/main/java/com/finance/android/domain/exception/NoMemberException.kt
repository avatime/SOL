package com.finance.android.domain.exception

import com.google.gson.annotations.SerializedName

data class NoMemberException(
    @SerializedName("token_id")
    val tokenId: Int
)
