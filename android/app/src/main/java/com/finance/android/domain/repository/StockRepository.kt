package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.RegisterStockAccountRequestDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import retrofit2.http.Body

interface StockRepository {
    suspend fun getStockAccountList(): MutableList<BankAccountResponseDto>
    suspend fun putRegisterStockAccount(@Body dto: Array<RegisterStockAccountRequestDto>)
}