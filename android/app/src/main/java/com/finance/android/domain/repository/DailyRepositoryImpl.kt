package com.finance.android.domain.repository

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

    override suspend fun test() {
        return dailyService.test()
    }
}
