package com.finance.backend.remit

import com.finance.backend.bank.response.RecentTradeRes
import com.finance.backend.remit.request.RemitInfoReq

interface RemitService {
    fun getRecommendationAccount(token: String): List<RecentTradeRes>
    fun postRemit(remitInfoReq: RemitInfoReq)
    fun putBookmark(acNo: String, token: String)
}