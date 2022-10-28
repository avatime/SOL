package com.finance.backend.cardPaymentHistory

import com.finance.backend.cardProduct.CardProduct
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface CardPaymentHistoryRepository: JpaRepository<CardPaymentHistory, Long> {
    fun findAllByCdNoAndCdPyDtBetween(cdNo: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<CardPaymentHistory>?
}