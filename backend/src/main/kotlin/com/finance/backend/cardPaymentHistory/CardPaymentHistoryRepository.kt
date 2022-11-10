package com.finance.backend.cardPaymentHistory

import com.finance.backend.cardProduct.CardProduct
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface CardPaymentHistoryRepository: JpaRepository<CardPaymentHistory, Long> {

    fun findAllByCardCdNo(cdNo: String) : List<CardPaymentHistory>?

    @Query("SELECT SUM(cph.cd_val) FROM card_payment_history cph WHERE cph.cd_no = :cdNo AND cph.cd_py_dt BETWEEN :startDateTime AND :endDateTime", nativeQuery = true)
    fun getByCdVal(cdNo: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): Int
}