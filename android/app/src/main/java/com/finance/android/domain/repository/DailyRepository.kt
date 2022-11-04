package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.dto.response.DailyProfileResponseDto
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import java.time.Month

interface DailyRepository {
    suspend fun attendance()
    suspend fun getAttendanceList(year : Int, month : Int) : MutableList<DailyAttendanceResponseDto>
    suspend fun getWalkingList(year: Int, month: Int): MutableList<DailyWalkingResponseDto>
    suspend fun getProfileList() : MutableList<DailyProfileResponseDto>
    suspend fun test()
}
