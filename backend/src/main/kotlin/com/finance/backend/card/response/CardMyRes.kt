package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CardMyRes (
    @JsonProperty("cd_val_all")
    val cdValAll : Int,

    @JsonProperty("card_res")
    val cardRes : CardRes
)