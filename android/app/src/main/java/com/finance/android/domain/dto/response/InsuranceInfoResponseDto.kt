package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class InsuranceInfoResponseDto(
    @SerializedName("is_id")
    val isId: Long, // 아이디
    @SerializedName("is_pd_name")
    val isPdName: String, //보험 이름
    @SerializedName("is_pd_fee")
    val isPdFee: Int, //보험료
    @SerializedName("name")
    val name: String, //이름
    @SerializedName("is_name")
    val isName: String, //피보험
    @SerializedName("is_reg")
    val isRegister: Boolean // 어플 등록 여부
)
