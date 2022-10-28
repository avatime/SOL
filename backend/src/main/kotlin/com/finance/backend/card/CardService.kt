package com.finance.backend.card

import com.finance.backend.card.response.CardBillDetailRes
import com.finance.backend.card.response.CardBillRes
import com.finance.backend.card.response.CardInfoRes

interface CardService {
    fun registerMain(cdNoList: List<String>)
    fun getAssetCard(token: String): List<CardInfoRes>
    fun getCardMonthInfo(cdNo: String, year:Int, month: Int): List<CardBillDetailRes>
    fun getCardMonthAll(cdNo: String, year:Int, month: Int): CardBillRes
}