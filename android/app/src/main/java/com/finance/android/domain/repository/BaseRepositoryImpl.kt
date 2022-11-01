package com.finance.android.domain.repository

import com.finance.android.domain.dto.response.ReissueTokenResponseDto
import com.finance.android.domain.service.BaseService
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val baseService: BaseService
) : BaseRepository {
    override suspend fun reissueToken(): ReissueTokenResponseDto {
        return baseService.refreshToken();
    }
}
