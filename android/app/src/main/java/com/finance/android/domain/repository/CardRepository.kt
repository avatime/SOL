package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.CardNumberDto
import com.finance.android.domain.dto.response.*

interface CardRepository {
    suspend fun getCardList(): MutableList<CardInfoResponseDto>
    suspend fun getMyCardList(): MutableList<CardResponseDto>
    suspend fun putRegisterCard(cardNumberDtoArray: Array<CardNumberDto>)
    suspend fun getCardBill(cdNo: String, year: Int, month: Int): CardBillResponseDto
    suspend fun getCardBenefit(cardProductCode: Int): MutableList<CardBenefitInfoResponseDto>
}