package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.ChangeProfileRequestDto
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.dto.response.DailyProfileResponseDto
import com.finance.android.domain.dto.response.DailyWalkingResponseDto

interface DailyRepository {
    suspend fun attendance()
    suspend fun getAttendanceList(year : Int, month : Int) : Array<DailyAttendanceResponseDto>
    suspend fun getWalkingList(year: Int, month: Int): MutableList<DailyWalkingResponseDto>
    suspend fun getProfileList() : Array<DailyProfileResponseDto>
    suspend fun changeProfile(changeProfileRequestDto: ChangeProfileRequestDto)
    suspend fun test()
    suspend fun receiveWalkPoint()
}
