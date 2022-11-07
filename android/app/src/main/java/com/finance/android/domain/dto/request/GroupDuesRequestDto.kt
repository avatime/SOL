package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class GroupDuesRequestDto(
    @SerializedName("dues_id")
    val duesId : Int
)
