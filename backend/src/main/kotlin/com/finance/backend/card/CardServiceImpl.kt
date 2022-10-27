package com.finance.backend.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
        private val cardRepository: CardRepository
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
}