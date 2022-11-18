package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class CardBillDetailRes(
        cdPyDt: LocalDateTime,
        cdPyName: String,
        cdVal: Int,
        cdTp: Int
) {
    @JsonProperty("cd_py_dt")
    val cdPyDt: LocalDateTime = cdPyDt

    @JsonProperty("cd_py_name")
    val cdPyName: String = cdPyName

    @JsonProperty("cd_val")
    val cdVal: Int = cdVal

    @JsonProperty("cd_tp")
    val cdTp:Int = cdTp
}