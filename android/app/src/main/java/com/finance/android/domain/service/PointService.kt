package com.finance.android.domain.service

import com.finance.android.domain.dto.response.PointHistoryResponseDto
import com.finance.android.utils.Const
import retrofit2.http.GET

interface PointService {

    @GET("${Const.API_PATH}/point/all")
    suspend fun getAllPoint() : MutableList<PointHistoryResponseDto>

    @GET("${Const.API_PATH}/point/out")
    suspend fun getOutPoint() : MutableList<PointHistoryResponseDto>

    @GET("${Const.API_PATH}/point/in")
    suspend fun getInPoint() : MutableList<PointHistoryResponseDto>
}