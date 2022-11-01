package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty

class RecentTradeRes(
        acReceive : String,
        acNo : String,
        cpName : String,
        bkStatus: Boolean,
        cpLogo: String
) {
    @JsonProperty("ac_receive")
    val acReceive: String = acReceive

    @JsonProperty("ac_no")
    val acNo: String = acNo

    @JsonProperty("cp_name")
    val cpName: String = cpName

    @JsonProperty("bk_status")
    val bkStatus: Boolean = bkStatus

    @JsonProperty("cp_logo")
    val cpLogo: String = cpLogo
}