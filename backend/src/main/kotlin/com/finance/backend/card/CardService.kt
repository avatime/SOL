package com.finance.backend.card

import com.finance.backend.card.request.CardInfoReq
import com.finance.backend.card.response.*
import com.finance.backend.cardBenefit.response.CardBenefitDetailRes
import com.finance.backend.cardBenefit.response.CardBenefitInfo

interface CardService {
    fun registerMain(cdNoList: List<CardInfoReq>)
    fun getAssetCard(token: String): List<CardInfoRes>
    fun getMyCard(token: String): List<CardRes>
    fun getCardMonthInfo(cdNo: String): List<CardBillDetailRes>
    fun getCardMonthAll(cdNo: String, year:Int, month: Int): CardBillRes
    fun getCardBenefitDetail(cdPdCode: Long): List<CardBenefitDetailRes>
    fun getCardBenefitThree(cdPdCode: Long): List<CardBenefitInfo>
    fun getCardRecommend() : CardRecommendRes
}