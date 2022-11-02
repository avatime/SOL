package com.finance.android.domain.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyRepositoryImpl @Inject constructor(
    private val dailyRepository: DailyRepository
) : DailyRepository {
    override suspend fun attendance() {
        return dailyRepository.attendance()
    }
}
