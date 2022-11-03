package com.finance.backend.card

import com.finance.backend.card.response.CardBillDetailRes
import com.finance.backend.card.response.CardBillRes
import com.finance.backend.card.response.CardInfoRes
import com.finance.backend.card.response.CardRes
import com.finance.backend.cardBenefit.response.CardBenefitDetailRes
import com.finance.backend.cardBenefit.response.CardBenefitRes

interface CardService {
    fun registerMain(cdNoList: List<String>)
    fun getAssetCard(token: String): List<CardInfoRes>
    fun getMyCard(token: String): List<CardRes>
    fun getCardMonthInfo(cdNo: String, year:Int, month: Int): List<CardBillDetailRes>
    fun getCardMonthAll(cdNo: String, year:Int, month: Int): CardBillRes
    fun getCardBenefit(token: String):List<CardBenefitRes>
    fun getCardBenefitDetail(cdNo: String): CardBenefitDetailRes
}