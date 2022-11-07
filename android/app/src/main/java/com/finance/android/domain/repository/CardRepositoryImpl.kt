package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.CardNumberDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.domain.dto.response.CardResponseDto
import com.finance.android.domain.service.CardService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val cardService: CardService
) : CardRepository {
    override suspend fun getCardList(): MutableList<CardInfoResponseDto> {
        return cardService.getCardList()
    }

    override suspend fun getMyCardList(): MutableList<CardResponseDto> {
        return cardService.getMyCardList()
    }

    override suspend fun putRegisterCard(cardNumberDtoArray: Array<CardNumberDto>) {
        return cardService.putRegisterCard(cardNumberDtoArray)
    }
}
