package com.finance.backend.card

import com.finance.backend.card.request.CardInfoReq
import com.finance.backend.card.response.*
import com.finance.backend.cardBenefit.response.CardBenefitDetailRes
import com.finance.backend.cardBenefit.response.CardBenefitInfo
import com.finance.backend.cardBenefit.response.CardBenefitRes

interface CardService {
    fun registerMain(cdNoList: List<CardInfoReq>)
    fun getAssetCard(token: String): List<CardInfoRes>
    fun getMyCard(token: String): List<CardMyRes>
    fun getCardMonthInfo(cdNo: String, year:Int, month: Int): List<CardBillDetailRes>
    fun getCardMonthAll(cdNo: String, year:Int, month: Int): CardBillRes
//    fun getCardBenefit(token: String):List<CardBenefitRes>
    fun getCardBenefitDetail(cdNo: String): List<CardBenefitDetailRes>
    fun getCardBenefitThree(cdPdCode: Long): List<CardBenefitInfo>
}