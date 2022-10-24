package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

class BankTradeRes(
        tdDt : Date,
        tdValue : Int,
        tdCn : String,
        tdType : Int
) {
    @JsonProperty("td_dt")
    val tdDt : Date = tdDt

    @JsonProperty("td_value")
    val tdValue : Int = tdValue

    @JsonProperty("td_cn")
    val tdCd : String = tdCn

    @JsonProperty("td_type")
    val tdType : Int = tdType

}