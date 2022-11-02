package com.finance.android.domain.service

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DailyService {

    @POST("${Const.API_PATH}/daily")
    suspend fun attendance()

    @GET("${Const.API_PATH}/daily")
    suspend fun test()
}
