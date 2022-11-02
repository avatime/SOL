package com.finance.android.domain.repository

import com.finance.android.domain.dto.response.CardInfoResponseDto

interface CardRepository {
    suspend fun getCardList(): MutableList<CardInfoResponseDto>
}