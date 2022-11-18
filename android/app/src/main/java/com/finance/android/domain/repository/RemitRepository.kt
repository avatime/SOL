package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.AccountNumberDto
import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import retrofit2.http.Body
import retrofit2.http.Field

interface RemitRepository {
    suspend fun getRecommendedAccount(): MutableList<RecentTradeResponseDto>
    suspend fun postRemitToAccount(@Body remitInfoRequestDto: RemitInfoRequestDto)
    suspend fun postRemitToPhone(@Body remitPhoneRequestDto: RemitPhoneRequestDto)
    suspend fun putRemitBookmark(@Body accountNumberDto : AccountNumberDto)
}
