package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CardRes (
    @JsonProperty("cd_val_all")
    val cdValAll : Int,

    @JsonProperty("card_info_res")
    val cardInfoRes: CardInfoRes
)