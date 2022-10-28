package com.finance.backend.finance

import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.corporation.response.BankInfoRes

interface FinanceService {
    fun getFinanceInfo(): List<BankInfoRes>
    fun getFinanceAsset(token: String): List<BankAccountRes>
    fun putFinanceAsset(acNo: String)
}