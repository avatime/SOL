package com.finance.android.domain.service

import com.finance.android.domain.dto.request.CardNumberDto
import com.finance.android.domain.dto.response.*
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface CardService {
    @GET("${Const.API_PATH}/card/asset")
    suspend fun getCardList(): MutableList<CardInfoResponseDto>

    @GET("${Const.API_PATH}/card/asset/my")
    suspend fun getMyCardList(): MutableList<CardResponseDto>

    @PUT("${Const.API_PATH}/card/asset")
    suspend fun putRegisterCard(@Body cardNumberDtoArray: Array<CardNumberDto>)

    @GET("${Const.API_PATH}/card/bill/{cd_no}/{year}/{month}")
    suspend fun getCardBill(
        @Path("cd_no") cdNo: String,
        @Path("year") year: Int,
        @Path("month") month: Int
    ): CardBillResponseDto

    @GET("${Const.API_PATH}/card/benefit/{cd_pd_code}")
    suspend fun getCardBenefit(@Path("cd_pd_code") cardProductCode: Int): MutableList<CardBenefitInfoResponseDto>

    @GET("${Const.API_PATH}/card/bill/detail/{cd_no}")
    suspend fun getCardHistory(@Path("cd_no") cdNo: String): MutableList<CardBillDetailResponseDto>

    @GET("${Const.API_PATH}/card/recommend")
    suspend fun getCardRecommend(): CardRecommendResponseDto

}