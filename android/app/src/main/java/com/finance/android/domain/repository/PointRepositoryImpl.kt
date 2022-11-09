package com.finance.android.domain.repository

import com.finance.android.domain.dto.response.PointHistoryResponseDto
import com.finance.android.domain.service.PointService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointRepositoryImpl @Inject constructor(
    private val pointService: PointService
) : PointRepository {
    override suspend fun getPointAllList(): MutableList<PointHistoryResponseDto> {
        return pointService.getAllPoint()
    }
}