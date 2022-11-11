package com.finance.backend.cardBenefit

import com.finance.backend.card.Card
import org.springframework.data.jpa.repository.JpaRepository

interface CardBenefitRepository: JpaRepository<CardBenefit, Long> {
    fun findAllByCardProductCdPdCode(cdPdCode: Long): List<CardBenefit>?
    fun findTop3ByCardProductCdPdCode(cdPdCode: Long): List<CardBenefit>
    fun findByCardProductCdPdCode(cdPdCode: Long): CardBenefit?
}