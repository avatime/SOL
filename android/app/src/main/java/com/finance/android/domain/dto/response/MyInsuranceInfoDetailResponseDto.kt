package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class MyInsuranceInfoDetailResponseDto(
    @SerializedName("is_id")
    val isId : Long, // 아이디
    @SerializedName("is_pd_name")
    val isPdName : String, //보험 이름
    @SerializedName("is_pd_fee")
    val isPdFee : Int, //보험료
    val name : String, //이름
    @SerializedName("is_name")
    val isName : String //피보험
)
