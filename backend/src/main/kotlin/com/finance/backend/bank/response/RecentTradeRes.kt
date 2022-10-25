package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty

class RecentTradeRes(
        acName : String,
        acNo : String,
        bkStatus: Boolean,
        cpLogo: String
) {
    @JsonProperty("ac_name")
    val acName: String = acName

    @JsonProperty("ac_no")
    val acNo: String = acNo

    @JsonProperty("bk_status")
    val bkStatus: Boolean = bkStatus

    @JsonProperty("cp_logo")
    val cpLogo: String = cpLogo
}