package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.ChangeProfileRequestDto
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.dto.response.DailyProfileResponseDto
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.finance.android.domain.service.DailyService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyRepositoryImpl @Inject constructor(
    private val dailyService: DailyService
) : DailyRepository {
    override suspend fun attendance() {
        return dailyService.attendance()
    }

    override suspend fun getAttendanceList(
        year: Int,
        month: Int
    ): MutableList<DailyAttendanceResponseDto> {
        return dailyService.getAttendanceList(year, month)
    }

    override suspend fun getWalkingList(
        year: Int,
        month: Int
    ): MutableList<DailyWalkingResponseDto> {
        return dailyService.getWalkingList(year, month)
    }

    override suspend fun getProfileList(): MutableList<DailyProfileResponseDto> {
        return dailyService.getProfileList()
    }

    override suspend fun changeProfile(changeProfileRequestDto: ChangeProfileRequestDto) {
        return dailyService.changeProfile(changeProfileRequestDto)
    }

    override suspend fun test() {
        return dailyService.test()
    }

    override suspend fun receiveWalkPoint() {
        return dailyService.receiveWalkPoint()
    }
}
