package com.finance.backend.finance

import com.finance.backend.corporation.response.BankInfoRes

interface FinanceService {
    fun getFinanceInfo(): List<BankInfoRes>
}