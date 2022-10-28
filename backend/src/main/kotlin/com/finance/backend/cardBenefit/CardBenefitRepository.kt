package com.finance.backend.cardBenefit

import org.springframework.data.jpa.repository.JpaRepository

interface CardBenefitRepository: JpaRepository<CardBenefit, Long> {
    fun findByCdPdCode(cdPdCode: Long): CardBenefit
}