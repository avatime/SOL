package com.finance.backend.card

import com.finance.backend.Exceptions.NoCardException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.card.request.CardInfoReq
import com.finance.backend.card.response.CardBillDetailRes
import com.finance.backend.card.response.CardBillRes
import com.finance.backend.card.response.CardInfoRes
import com.finance.backend.card.response.CardRes
import com.finance.backend.cardBenefit.CardBenefitRepository
import com.finance.backend.cardBenefit.response.CardBenefitDetailRes
import com.finance.backend.cardBenefit.response.CardBenefitInfo
import com.finance.backend.cardBenefit.response.CardBenefitRes
import com.finance.backend.cardBenefitImg.CardBenefitImgRepository
import com.finance.backend.cardPaymentHistory.CardPaymentHistoryRepository
import com.finance.backend.cardProduct.CardProductRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.user.UserRepository
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

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val cardList = cardRepository.findAllByUserIdAndCdReg(userId, true).orEmpty()
            for (card in cardList){
                val cardProduct = cardProductRepository.findById(card.cdPdCode).orElse(null)
                val cardInfoRes = CardInfoRes(cardProduct.cdImg, cardProduct.cdName, card.cdReg, card.cdNo)
                val cardBenefitInfoList = ArrayList<CardBenefitInfo>()

                val cardBenefitList = cardBenefitRepository.findTop3ByCardProductCdPdCode(card.cdPdCode)
                for (cardBenefit in cardBenefitList){
                    cardBenefitInfoList.add(CardBenefitInfo(cardBenefit.cdBfImg.cdBfImg, cardBenefit.cdBfSum, cardBenefit.cdBfName))
                }
                cardInfoList.add(CardRes(cardInfoRes, cardBenefitInfoList))
            }
        }
        return cardInfoList
    }

    override fun getCardMonthInfo(cdNo: String, year: Int, month: Int): List<CardBillDetailRes> {
        val cardBillDetailList = ArrayList<CardBillDetailRes>()
        val startDate = LocalDate.of(year, month, 1)
        val endDate = startDate.plusMonths(1).minusDays(1)
        val cardProductHistoryList = cardPaymentHistoryRepository.findAllByCardCdNoAndCdPyDtBetween(cdNo, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX))?: emptyList()
        for(cardProductHistory in cardProductHistoryList){
            val cardBillDetailRes = CardBillDetailRes(cardProductHistory.cdPyDt, cardProductHistory.cdPyName, cardProductHistory.cdVal, cardProductHistory.cdTp)
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

//    override fun getCardBenefit(token: String): List<CardBenefitRes> {
//        val cardBenefitList = ArrayList<CardBenefitRes>()
//        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
//            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
//            val cardList = cardRepository.findAllByUserId(userId)
//            for (card in cardList){
//                val cardProduct = cardProductRepository.findById(card.cdPdCode).get()
//                val cardBenefitList = cardBenefitRepository.findAllByCardProductCdPdCode(card.cdPdCode)
//                val cardBenefitImg = cardBenefitImgRepository.findById(cardBenefit.cdBfImg.id).get()
//                val corporation = corporationRepository.findById(cardProduct.cdPdCode).get()
//                val cardBenefitRes = CardBenefitRes(corporation.cpName, cardBenefit.cdBfName, cardBenefit.cdBfSum, cardBenefitImg.cdBfImg)
//                cardBenefitList.add(cardBenefitRes)
//            }
//        }
//        return cardBenefitList
//    }

    override fun getCardBenefitDetail(cdNo: String): List<CardBenefitDetailRes> {
        val cardBenefitDetailList = ArrayList<CardBenefitDetailRes>()
        val cdPdCode = cardRepository.findById(cdNo).orElse(null).cdPdCode
        val cardBenefitList = cardBenefitRepository.findAllByCardProductCdPdCode(cdPdCode).orEmpty()
        for (cardBenefit in cardBenefitList){
            val cardBenefitImg = cardBenefitImgRepository.findById(cardBenefit.cdBfImg.id).orElse(null)
            cardBenefitDetailList.add(CardBenefitDetailRes(cardBenefit.cdBfName, cardBenefit.cdBfSum, cardBenefitImg.cdBfImg, cardBenefit.cdBfDetail))
        }
        return cardBenefitDetailList
    }

    override fun getCardBenefitThree(cdNo: String): List<CardBenefitInfo> {
        val cardBenefitInfoList = ArrayList<CardBenefitInfo>()
        val cdPdCode = cardRepository.findById(cdNo).orElse(null).cdPdCode
        val cardBenefitList = cardBenefitRepository.findTop3ByCardProductCdPdCode(cdPdCode).orEmpty()
        for (cardBenefit in cardBenefitList){
            val cardBenefitImg = cardBenefitImgRepository.findById(cardBenefit.cdBfImg.id).orElse(null)
            cardBenefitInfoList.add(CardBenefitInfo(cardBenefitImg.cdBfImg, cardBenefit.cdBfSum, cardBenefit.cdBfName))
        }

        return cardBenefitInfoList
    }

}