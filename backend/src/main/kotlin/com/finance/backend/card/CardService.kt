package com.finance.backend.card

import com.finance.backend.card.response.CardInfoRes

interface CardService {
    fun registerMain(cdNoList: List<String>)
    fun getAssetCard(token: String): List<CardInfoRes>
}