package com.finance.backend.card

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.card.response.CardInfoRes
import com.finance.backend.cardProduct.CardProductRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class CardServiceImpl(
        private val cardRepository: CardRepository,
        private val userRepository: UserRepository,
        private val cardProductRepository: CardProductRepository,
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
}