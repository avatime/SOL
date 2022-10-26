package com.finance.backend.remit

import com.finance.backend.bank.response.RecentTradeRes

interface RemitService {
    fun getRecommendationAccount(token: String): List<RecentTradeRes>
}