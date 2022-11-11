package com.finance.backend.card

import com.finance.backend.Exceptions.NoCardException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.card.request.CardInfoReq
import com.finance.backend.card.response.*
import com.finance.backend.cardBenefit.CardBenefitRepository
import com.finance.backend.cardBenefit.response.CardBenefitDetailRes
import com.finance.backend.cardBenefit.response.CardBenefitInfo
import com.finance.backend.cardBenefitImg.CardBenefitImgRepository
import com.finance.backend.cardPaymentHistory.CardPaymentHistoryRepository
import com.finance.backend.cardProduct.CardProductRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

@Service
class CardServiceImpl(
        private val cardRepository: CardRepository,
        private val cardProductRepository: CardProductRepository,
        private val cardPaymentHistoryRepository: CardPaymentHistoryRepository,
        private val cardBenefitRepository: CardBenefitRepository,
        private val cardBenefitImgRepository: CardBenefitImgRepository,
        private val corporationRepository: CorporationRepository,
        private val jwtUtils: JwtUtils,

) : CardService {
    override fun registerMain(cdNoList: List<CardInfoReq>) {
        for (c in cdNoList){
            var card: Card = cardRepository.findById(c.cdNo).orElse(null)?: throw NoCardException()
            card.register()
            cardRepository.save(card)
        }
    }

    override fun getAssetCard(token: String): List<CardInfoRes> {
        val cardInfoList = ArrayList<CardInfoRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val cardList = cardRepository.findAllByUserId(userId)
            for (card in cardList){
                val cardProduct = cardProductRepository.findById(card.cdPdCode).get()
                val cardInfoRes = CardInfoRes(cardProduct.cdImg, cardProduct.cdName, card.cdReg, card.cdNo)
                cardInfoList.add(cardInfoRes)
            }
        }
        return cardInfoList
    }

    override fun getMyCard(token: String): List<CardRes> {
        val cardInfoList = ArrayList<CardRes>()
        val c = cardBenefitRepository.findTop3ByCardProductCdPdCode(199)
        val now = LocalDateTime.now()
        val year = now.year
        val month = now.month

        val startDate = LocalDateTime.of(year, month, 1, 0, 0, 0)
        val endDate = startDate.plusMonths(1).minusSeconds(1)

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val cardList = cardRepository.findAllByUserIdAndCdReg(userId, true).orEmpty()
            for (card in cardList){
                val cardProduct = cardProductRepository.findById(card.cdPdCode).orElse(null)
                val cardInfoRes = CardInfoRes(cardProduct.cdImg, cardProduct.cdName, card.cdReg, card.cdNo)

                val balance = cardPaymentHistoryRepository.getByCdVal(card.cdNo,startDate,endDate)
                cardInfoList.add(CardRes(balance, cardInfoRes))
            }
        }
        return cardInfoList
    }

    override fun getCardMonthInfo(cdNo: String): List<CardBillDetailRes> {
        val cardBillDetailList = ArrayList<CardBillDetailRes>()
        val cardProductHistoryList = cardPaymentHistoryRepository.findAllByCardCdNo(cdNo)?: emptyList()
        for(cardProductHistory in cardProductHistoryList){

            val cardBillDetailRes = CardBillDetailRes(cardProductHistory.cdPyDt, cardProductHistory.cdPyName, cardProductHistory.cdVal * -1, cardProductHistory.cdTp)
            cardBillDetailList.add(cardBillDetailRes)
        }
        return cardBillDetailList
    }

    override fun getCardMonthAll(cdNo: String, year:Int, month: Int): CardBillRes {
        val cpCode = cardRepository.findById(cdNo).get().cdPdCode
        val cardProduct = cardProductRepository.findById(cpCode).get()
        val startDate = LocalDateTime.of(year, month, 1, 0, 0, 0)
        val endDate = startDate.plusMonths(1).minusSeconds(1)
        val tdDt = LocalDate.of(year, month, 10)
        val tdVal = cardPaymentHistoryRepository.getByCdVal(cdNo, startDate, endDate)

        val cardBillRes = CardBillRes(cdNo,cardProduct.cdName, cardProduct.cdImg, tdVal, tdDt)

        return cardBillRes
    }

    override fun getCardBenefitDetail(cdPdCode: Long): List<CardBenefitDetailRes> {
        val cardBenefitDetailList = ArrayList<CardBenefitDetailRes>()
        val cardBenefitList = cardBenefitRepository.findAllByCardProductCdPdCode(cdPdCode).orEmpty()
        for (cardBenefit in cardBenefitList){
            val cardBenefitImg = cardBenefitImgRepository.findById(cardBenefit.cdBfImg.id).orElse(null)
            cardBenefitDetailList.add(CardBenefitDetailRes(cardBenefit.cdBfName, cardBenefit.cdBfSum, cardBenefitImg.cdBfImg, cardBenefit.cdBfDetail))
        }
        return cardBenefitDetailList
    }

    override fun getCardBenefitThree(cdPdCode: Long): List<CardBenefitInfo> {
        val cardBenefitInfoList = ArrayList<CardBenefitInfo>()
        val cardBenefitList = cardBenefitRepository.findTop3ByCardProductCdPdCode(cdPdCode)
        for (cardBenefit in cardBenefitList){
            val cardBenefitImg = cardBenefitImgRepository.findById(cardBenefit.cdBfImg.id).orElse(null)
            if(cardBenefit.cdBfName != "유의사항") cardBenefitInfoList.add(CardBenefitInfo(cardBenefitImg.cdBfImg, cardBenefit.cdBfSum, cardBenefit.cdBfName))
        }

        return cardBenefitInfoList
    }

    override fun getCardRecommend(): CardRecommendRes {
        val creditInfoList = ArrayList<CardRecommendInfoRes>()
        val checkInfoList = ArrayList<CardRecommendInfoRes>()
        val creditList = mutableSetOf<Int>()
        while (creditList.size < 10){ creditList.add((1..99).random())}
        val checkList = mutableSetOf<Int>()
        while (checkList.size < 10){ checkList.add((100..199).random())}

        for (credit in creditList){
            val cardProduct = cardProductRepository.findByCdPdCode(credit.toLong())
            val cardBenefitList = cardBenefitRepository.findTop3ByCardProductCdPdCode(credit.toLong())!!
            val cardBenefitInfoList = ArrayList<CardBenefitInfo>()
            for (cardBenefit in cardBenefitList){
                cardBenefitInfoList.add(CardBenefitInfo(cardBenefit.cdBfImg.cdBfImg, cardBenefit.cdBfSum, cardBenefit.cdBfName))
            }

            creditInfoList.add(CardRecommendInfoRes(cardProduct.cdPdCode, cardProduct.cdName, cardProduct.cdImg, cardBenefitInfoList))
        }

        for (check in checkList){
            val cardProduct = cardProductRepository.findByCdPdCode(check.toLong())
            val cardBenefitList = cardBenefitRepository.findTop3ByCardProductCdPdCode(check.toLong())!!
            val cardBenefitInfoList = ArrayList<CardBenefitInfo>()
            for (cardBenefit in cardBenefitList){
                cardBenefitInfoList.add(CardBenefitInfo(cardBenefit.cdBfImg.cdBfImg, cardBenefit.cdBfSum, cardBenefit.cdBfName))
            }
            checkInfoList.add(CardRecommendInfoRes(cardProduct.cdPdCode, cardProduct.cdName, cardProduct.cdImg, cardBenefitInfoList))
        }

        return CardRecommendRes(creditInfoList, checkInfoList)
    }
}