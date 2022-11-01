package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class BankTradeResponseDto(

    @SerializedName("td_dt")
    val tdDT : Date, //거래일자
    @SerializedName("td_value")
    val tdValue : Int, //거래액
    @SerializedName("td_cn")
    val tdCn : String, //거래 내역
    @SerializedName("td_type")
    val tdType : String //거래 타입(입금, 출금, 송금, 수금)

)

