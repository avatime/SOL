package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CardInfoResponseDto(
    @SerializedName("cd_img") // 카드 이미지
    val cardImgPath: String,
    @SerializedName("cd_name") // 카드 이름
    val cardName: String,
    @SerializedName("cd_no") // 카드 번호
    val cardNumber: String,
    @SerializedName("cd_reg")
    val isRegister: Boolean // 등록 여부
)
