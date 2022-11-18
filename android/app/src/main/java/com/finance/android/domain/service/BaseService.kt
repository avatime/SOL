package com.finance.android.domain.service

import com.finance.android.domain.dto.response.ReissueTokenResponseDto
import com.finance.android.utils.Const
import retrofit2.http.POST

interface BaseService {
    @POST("${Const.API_PATH}/auth/refresh")
    suspend fun refreshToken(): ReissueTokenResponseDto
}