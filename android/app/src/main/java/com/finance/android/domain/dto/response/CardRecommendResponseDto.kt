package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class CardRecommendResponseDto (
    @SerializedName("credit_card_list")
    val creditCardList : MutableList<CardRecommendInfoResponseDto>,
    @SerializedName("check_card_list")
    val checkCardList : MutableList<CardRecommendInfoResponseDto>,
)