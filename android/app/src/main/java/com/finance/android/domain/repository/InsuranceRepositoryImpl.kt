package com.finance.android.domain.repository

import com.finance.android.domain.service.InsuranceService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsuranceRepositoryImpl @Inject constructor(
    private val insuranceService: InsuranceService
) : InsuranceRepository
