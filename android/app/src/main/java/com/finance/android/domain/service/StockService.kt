package com.finance.android.domain.service

import com.finance.android.domain.dto.request.RegisterStockAccountRequestDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface StockService {
    @GET("${Const.API_PATH}/finance/asset")
    suspend fun getStockAccountList(): MutableList<BankAccountResponseDto>

    @PUT("${Const.API_PATH}/finance/asset")
    suspend fun putRegisterStockAccount(@Body dto: Array<RegisterStockAccountRequestDto>)
}
