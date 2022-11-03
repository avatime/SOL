package com.finance.android.domain.service

import com.finance.android.domain.dto.request.InsuranceIdRequestDto
import com.finance.android.domain.dto.response.InsuranceInfoResponseDto
import com.finance.android.domain.dto.response.MyInsuranceInfoResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface InsuranceService {
    @PUT("${Const.API_PATH}/insurance/my")
    suspend fun putRegisterInsurance(@Body insuranceIdRequestDtoArray: Array<InsuranceIdRequestDto>)

    @GET("${Const.API_PATH}/insurance/my/all")
    suspend fun getInsuranceList(): MutableList<InsuranceInfoResponseDto>

    @GET("${Const.API_PATH}/insurance/my")
    suspend fun getMyInsuranceList(): MutableList<MyInsuranceInfoResponseDto>
}