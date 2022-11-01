package com.finance.android.domain.service

import com.finance.android.domain.dto.response.ReissueTokenResponseDto
import retrofit2.http.POST

interface BaseService {
    @POST("auth/refresh")
    suspend fun refreshToken(): ReissueTokenResponseDto
}