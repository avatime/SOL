package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class GroupIdRequestDto(
    @SerializedName("pa_id")
    val paId : Int
)
