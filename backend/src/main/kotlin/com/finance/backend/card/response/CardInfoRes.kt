package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty

class CardInfoRes(
        img: String,
        name: String,
        cdReg: Boolean,
        cdNo: String,
        cdPd: Long,
) {
    @JsonProperty("cd_img")
    val cdImg: String = img

    @JsonProperty("cd_name")
    val cdName: String = name

    @JsonProperty("cd_reg")
    val cdReg = cdReg

    @JsonProperty("cd_no")
    val cdNo = cdNo

    @JsonProperty("cd_pd")
    val cdPd = cdPd
}