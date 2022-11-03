package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.StockAccountNumberDto
import com.finance.android.domain.dto.response.BankAccountResponseDto

interface StockRepository {
    suspend fun getStockAccountList(): MutableList<BankAccountResponseDto>
    suspend fun putRegisterStockAccount(stockAccountNumberDtoArray: Array<StockAccountNumberDto>)
}