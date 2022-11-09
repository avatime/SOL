package com.finance.android.domain.repository

import com.finance.android.domain.dto.response.PointHistoryResponseDto

interface PointRepository {
    suspend fun getPointAllList() : MutableList<PointHistoryResponseDto>
}