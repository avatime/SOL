package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.Date

class BankTradeRes(
        tdDt : LocalDateTime,
        tdValue : Long,
        tdCn : String,
        tdType : Int
) {
    @JsonProperty("td_dt")
    val tdDt : LocalDateTime = tdDt

    @JsonProperty("td_value")
    val tdValue : Long = tdValue

    @JsonProperty("td_cn")
    val tdCd : String = tdCn

    @JsonProperty("td_type")
    val tdType : Int = tdType

}