package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface DailyRepository {
    suspend fun attendance()
    suspend fun test()
}
