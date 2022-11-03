package com.finance.android.domain.service

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.utils.Const
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DailyService {

    @POST("${Const.API_PATH}/daily")
    suspend fun attendance()

    @GET("${Const.API_PATH}/daily/{year}/{month}")
    suspend fun getAttendanceList(@Path("year") year: Int, @Path("month") month: Int) : MutableList<DailyAttendanceResponseDto>

    @GET("${Const.API_PATH}/daily")
    suspend fun test()
}
