package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.InsuranceIdRequestDto
import com.finance.android.domain.dto.response.InsuranceInfoResponseDto
import com.finance.android.domain.dto.response.MyInsuranceInfoResponseDto
import com.finance.android.domain.service.InsuranceService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsuranceRepositoryImpl @Inject constructor(
    private val insuranceService: InsuranceService
) : InsuranceRepository {
    override suspend fun putRegisterInsurance(insuranceIdRequestDtoArray: Array<InsuranceIdRequestDto>) {
        return insuranceService.putRegisterInsurance(insuranceIdRequestDtoArray)
    }

    override suspend fun getInsuranceList(): MutableList<InsuranceInfoResponseDto> {
        return insuranceService.getInsuranceList()
    }

    override suspend fun getMyInsurance(): MyInsuranceInfoResponseDto {
        return insuranceService.getMyInsurance()
    }
}
