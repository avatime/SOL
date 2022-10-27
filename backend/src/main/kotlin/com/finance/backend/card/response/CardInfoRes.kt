package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty

class CardInfoRes(
        img: String,
        name: String
) {
    @JsonProperty("cd_img")
    val cdImg: String = img

    @JsonProperty("cd_name")
    val cdName: String = name
}