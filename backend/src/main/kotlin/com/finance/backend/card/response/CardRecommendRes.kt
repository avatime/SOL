package com.finance.backend.card.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CardRecommendRes(
    @JsonProperty("credit_card_list")
    val creditCardList : List<CardRecommendInfoRes>,

    @JsonProperty("check_card_list")
    val checkCardList : List<CardRecommendInfoRes>
)
