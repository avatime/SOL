package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.StockAccountNumberDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.FinanceDetailResponseDto
import com.finance.android.domain.dto.response.FinanceResponseDto
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
    override suspend fun getFinanceList(): Array<FinanceResponseDto> {
        return stockService.getFinanceList()
    }

    override suspend fun getHomeFinanceList(): MutableList<FinanceResponseDto> {
        return stockService.getHomeFinanceList()
    }

    override suspend fun getFinanceDetailList(fnName: String): Array<FinanceDetailResponseDto> {
        return stockService.getFinanceDetailList(fnName)
    }

    override suspend fun getMyFinanceList(): MutableList<BankAccountResponseDto> {
        return stockService.getMyFinanceList()
    }
}
