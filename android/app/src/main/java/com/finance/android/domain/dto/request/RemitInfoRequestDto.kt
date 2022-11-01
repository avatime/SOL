package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class RemitInfoRequestDto (
    @SerializedName("ac_name") // 보내는 계좌 은행
    val acName : String,
    @SerializedName("ac_tag") //받는 계좌 은행
    val acTag : String,
    @SerializedName("ac_send") //보내는 계좌 번호
    val acSend : String,
    @SerializedName("ac_receive") // 받는 계좌 번호
    val acReceive : String,
    val value : Int, // 금액
    val receive : String, // 나에게 표시
    val send : String // 받는 분에게 표시

    )
