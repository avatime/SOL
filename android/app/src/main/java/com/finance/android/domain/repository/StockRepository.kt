package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.StockAccountNumberDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.FinanceDetailResponseDto
import com.finance.android.domain.dto.response.FinanceResponseDto

interface StockRepository {
    suspend fun getStockAccountList(): MutableList<BankAccountResponseDto>
    suspend fun putRegisterStockAccount(stockAccountNumberDtoArray: Array<StockAccountNumberDto>)
    suspend fun getFinanceList(): Array<FinanceResponseDto>
    suspend fun getHomeFinanceList(): Array<FinanceResponseDto>
    suspend fun getFinanceDetailList(fnName: String) : Array<FinanceDetailResponseDto>
    suspend fun getMyFinanceList(): MutableList<BankAccountResponseDto>
}