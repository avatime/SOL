package com.finance.backend.cardPaymentHistory

import com.finance.backend.cardProduct.CardProduct
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface CardPaymentHistoryRepository: JpaRepository<CardPaymentHistory, Long> {
    fun findAllByCardCdNoAndCdPyDtBetween(cdNo: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<CardPaymentHistory>?


    @Query("SELECT SUM(cph.cd_val) FROM CardPaymentHistory cph WHERE cph.cd_py_dt BETWEEN :startDateTime AND :endDateTime", nativeQuery = true)
    fun getByCdVal(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Int
}