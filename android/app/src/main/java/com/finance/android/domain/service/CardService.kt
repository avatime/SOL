package com.finance.android.domain.service

import com.finance.android.domain.dto.request.CardNumberDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.domain.dto.response.CardResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface CardService {
    @GET("${Const.API_PATH}/card/asset")
    suspend fun getCardList(): MutableList<CardInfoResponseDto>

    @GET("${Const.API_PATH}/card/asset/my")
    suspend fun getMyCardList(): MutableList<CardResponseDto>

    @PUT("${Const.API_PATH}/card/asset")
    suspend fun putRegisterCard(@Body cardNumberDtoArray: Array<CardNumberDto>)
}