package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.service.RemitService
import javax.inject.Inject

class RemitRepositoryImpl @Inject constructor(
    private val remitService: RemitService
) : RemitRepository {
    override suspend fun getRecommendedAccount(): MutableList<RecentTradeResponseDto> {
        return remitService.getRecommendedAccount()
    }

    override suspend fun postRemitToAccount(remitInfoRequestDto: RemitInfoRequestDto) {
        return remitService.postRemitToAccount(remitInfoRequestDto)
    }

    override suspend fun postRemitToPhone(remitPhoneRequestDto: RemitPhoneRequestDto) {
        return remitService.postRemitToPhone(remitPhoneRequestDto)
    }

    override suspend fun putRemitBookmark(acNo: String) {
        remitService.putRemitBookmark(acNo)
    }
}