package com.finance.backend.cardProduct

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface CardProductRepository: JpaRepository<CardProduct, Long> {
    fun findByCdPdCode(cdPdCode : Long) : CardProduct
}