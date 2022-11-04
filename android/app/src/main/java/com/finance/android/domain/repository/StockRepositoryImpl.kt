package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.StockAccountNumberDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.service.StockService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockService: StockService
) : StockRepository {
    override suspend fun getStockAccountList(): MutableList<BankAccountResponseDto> {
        return stockService.getStockAccountList()
    }

    override suspend fun putRegisterStockAccount(stockAccountNumberDtoArray: Array<StockAccountNumberDto>) {
        return stockService.putRegisterStockAccount(stockAccountNumberDtoArray)
    }
}
