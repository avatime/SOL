package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime

class CardBillRes(
        cdNo: String,
        cdName: String,
        cdImg: String,
        tdVal: Int,
        tdDt: LocalDate
) {
    @JsonProperty("cd_no")
    val cdNo: String = cdNo

    @JsonProperty("cd_name")
    val cdName: String = cdName

    @JsonProperty("cd_img")
    val cdImg: String = cdImg

    @JsonProperty("td_val")
    val tdVal: Int = tdVal

    @JsonProperty("td_dt")
    val tdDt: LocalDate = tdDt
}