package com.finance.backend.card

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.card.response.CardBillDetailRes
import com.finance.backend.card.response.CardBillRes
import com.finance.backend.card.response.CardInfoRes
import com.finance.backend.cardPaymentHistory.CardPaymentHistoryRepository
import com.finance.backend.cardProduct.CardProductRepository
import com.finance.backend.common.util.JwtUtils
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
        private val jwtUtils: JwtUtils,

) : CardService {
    override fun registerMain(cdNoList: List<String>) {
        for (cdNo in cdNoList){
            var card: Card = cardRepository.findById(cdNo).get()
            card.apply {
                cdReg = true
            }
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
                val cardInfoRes = CardInfoRes(cardProduct.cdImg, cardProduct.cdName)
                cardInfoList.add(cardInfoRes)
            }
        }
        return cardInfoList
    }

    override fun getCardMonthInfo(cdNo: String, year: Int, month: Int): List<CardBillDetailRes> {
        val cardBillDetailList = ArrayList<CardBillDetailRes>()
        val startDate = LocalDate.of(year, month, 1)
        val endDate = startDate.plusMonths(1).minusDays(1)
        val cardProductHistoryList = cardPaymentHistoryRepository.findAllByCdNoAndCdPyDtBetween(cdNo, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX))?: emptyList()
        for(cardProductHistory in cardProductHistoryList){
            val cardBillDetailRes = CardBillDetailRes(cardProductHistory.cdPyDt, cardProductHistory.cdPyName, cardProductHistory.cdVal, cardProductHistory.cdTp)
            cardBillDetailList.add(cardBillDetailRes)
        }
        return cardBillDetailList
    }

    override fun getCardMonthAll(cdNo: String, year:Int, month: Int): CardBillRes {

        val cardProduct = cardProductRepository.findByCdNo(cdNo)
        val startDate = LocalDateTime.of(year, month, 1, 0, 0, 0)
        val endDate = startDate.plusMonths(1)
        val tdDt = LocalDate.of(year, month, 10)
        val tdVal = cardPaymentHistoryRepository.getByCdVal(startDate, endDate)

        val cardBillRes = CardBillRes(cdNo,cardProduct.cdName, cardProduct.cdImg, tdVal, tdDt)

        return cardBillRes
    }
}