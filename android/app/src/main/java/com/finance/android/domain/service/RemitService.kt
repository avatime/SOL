package com.finance.android.domain.service

import com.finance.android.domain.dto.request.AccountNumberDto
import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RemitService {
    @GET("${Const.API_PATH}/remit/recommendation")
    suspend fun getRecommendedAccount() : MutableList<RecentTradeResponseDto>

    @POST("${Const.API_PATH}/remit/account")
    suspend fun postRemitToAccount(@Body remitInfoRequestDto: RemitInfoRequestDto)

    @POST("${Const.API_PATH}/remit/phone")
    suspend fun postRemitToPhone(@Body remitPhoneRequestDto: RemitPhoneRequestDto)

    @PUT("${Const.API_PATH}/bank/bookmark")
    suspend fun putRemitBookmark(@Body accountNumberDto: AccountNumberDto)
}