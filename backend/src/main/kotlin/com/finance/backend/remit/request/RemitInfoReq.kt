package com.finance.backend.remit.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RemitInfoReq (
    @JsonProperty("ac_name")
    val acName: String = "",

    @JsonProperty("ac_tag")
    val acTag: String = "",

    @JsonProperty("ac_send")
    val acSend: String = "",

    @JsonProperty("ac_receive")
    val acReceive: String = "",

    @JsonProperty("value")
    val value: Long = 0,

    @JsonProperty("receive")
    val receive: String = "",

    @JsonProperty("send")
    val send: String = ""
)