package com.finance.backend.remit.request

import com.fasterxml.jackson.annotation.JsonProperty

class RemitPhoneReq {
    @JsonProperty("ac_name")
    val acName: String = ""

    @JsonProperty("ac_send")
    val acSend: String = ""

    val value: Long = 0

    val receive: String = ""

    val send: String = ""

    val phone: String = ""
}