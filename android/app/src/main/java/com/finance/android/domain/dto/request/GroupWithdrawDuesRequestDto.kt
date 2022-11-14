package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class GroupWithdrawDuesRequestDto(
    @SerializedName("pa_id")
    val paId : Int,
    @SerializedName("value")
    val value : Int
)
