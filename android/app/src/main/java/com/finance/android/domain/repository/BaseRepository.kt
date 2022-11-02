package com.finance.android.domain.repository

import com.finance.android.domain.dto.response.ReissueTokenResponseDto

interface BaseRepository {
    suspend fun reissueToken(): ReissueTokenResponseDto
}
