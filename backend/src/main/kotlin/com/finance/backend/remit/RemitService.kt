package com.finance.backend.remit

import com.finance.backend.bank.response.RecentTradeRes
import com.finance.backend.remit.request.RemitInfoReq
import com.finance.backend.remit.request.RemitNonMemberReq
import com.finance.backend.remit.request.RemitPhoneReq

interface RemitService {
    fun getRecommendationAccount(token: String): List<RecentTradeRes>
    fun postRemit(remitInfoReq: RemitInfoReq)
    fun postRemitPhone(remitPhoneReq: RemitPhoneReq)
    fun putBookmark(acNo: String, token: String)
    fun postRemitPhoneNonMember(remitNonMemberReq: RemitNonMemberReq)
    fun getRemitPhoneNonMember(tokenId : Long) : Boolean

}