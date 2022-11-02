package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CardInfoResponseDto(
    @SerializedName("cd_img")
    val cardImgPath: String,
    @SerializedName("cd_name")
    val cardName: String
)