package com.finance.android.domain.service

import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RemitService {
    @GET("remit/recommendation")
    suspend fun getRecommendedAccount() : MutableList<RecentTradeResponseDto>

    @POST("remit/account")
    suspend fun postRemitToAccount(@Body remitInfoRequestDto: RemitInfoRequestDto)

    @POST("remit/phone")
    suspend fun postRemitToPhone(@Body remitPhoneRequestDto: RemitPhoneRequestDto)

    @PUT("remit/bookmark")
    suspend fun putRemitBookmark(@Body acNo : String)
}