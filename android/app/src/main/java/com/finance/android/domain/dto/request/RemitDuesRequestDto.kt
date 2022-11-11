package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class RemitDuesRequestDto(
    @SerializedName("dues_id")
    val duesId : Int,

    @SerializedName("dues_val")
    val duesVal : Int,
//
//    @SerializedName("ac_no")
//    val acNo : String
)
