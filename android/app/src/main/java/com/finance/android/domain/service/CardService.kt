package com.finance.android.domain.service

import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.utils.Const
import retrofit2.http.GET

interface CardService {
    @GET("${Const.API_PATH}/card/asset")
    suspend fun getCardList(): MutableList<CardInfoResponseDto>
}