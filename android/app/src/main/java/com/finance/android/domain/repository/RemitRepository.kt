package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.Field

interface RemitRepository {
    suspend fun getRecommendedAccount() : Flow<Response<MutableList<RecentTradeResponseDto>>>
    suspend fun postRemitToAccount(@Body remitInfoRequestDto: RemitInfoRequestDto) : Flow<Response<Unit>>
    suspend fun postRemitToPhone(@Body remitPhoneRequestDto: RemitPhoneRequestDto) : Flow<Response<Unit>>
    suspend fun putRemitBookmark(@Field("ac_no") acNo : String) : Flow<Response<Unit>>
}