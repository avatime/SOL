package com.finance.backend.data.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class TradeReq(
        @JsonProperty("td_val")
        val tdVal: Int,

        @JsonProperty("td_cn")
        val tdCn: String,

        @JsonProperty("td_tg")
        val tdTg: String,

        @JsonProperty("td_tg_ac")
        val tdTgAc: String,

        @JsonProperty("td_rec")
        val tdRec: String,

        @JsonProperty("td_sed")
        val tdSed: String,

        @JsonProperty("ac_no")
        val acNo: String
)
