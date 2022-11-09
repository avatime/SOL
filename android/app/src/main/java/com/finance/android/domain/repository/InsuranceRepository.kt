package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.InsuranceIdRequestDto
import com.finance.android.domain.dto.response.InsuranceDetailResponseDto
import com.finance.android.domain.dto.response.InsuranceInfoResponseDto
import com.finance.android.domain.dto.response.MyInsuranceInfoResponseDto
import retrofit2.http.Body

interface InsuranceRepository {
    suspend fun putRegisterInsurance(@Body insuranceIdRequestDtoArray: Array<InsuranceIdRequestDto>)
    suspend fun getInsuranceList(): MutableList<InsuranceInfoResponseDto>
    suspend fun getMyInsurance(): MyInsuranceInfoResponseDto
    suspend fun getInsuranceDetail(id: Int): InsuranceDetailResponseDto
}