package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.CardNumberDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.domain.dto.response.CardResponseDto

interface CardRepository {
    suspend fun getCardList(): MutableList<CardInfoResponseDto>
    suspend fun getMyCardList(): MutableList<CardResponseDto>
    suspend fun putRegisterCard(cardNumberDtoArray: Array<CardNumberDto>)
}