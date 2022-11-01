package com.finance.backend.tradeHistory

import com.finance.backend.bank.Account
import com.finance.backend.tradeHistory.TradeHistory
import org.apache.ibatis.annotations.Param
import org.hibernate.sql.Select
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.Date

interface TradeHistoryRepository: JpaRepository<TradeHistory, Long> {
    fun findAllByAccountAcNo(acNo: String): List<TradeHistory>?
    fun findAllByAccountAndTdDtBetween(account: Account, start : LocalDateTime, end : LocalDateTime): List<TradeHistory>?
    fun findAllByAccountAndTdDtBetweenOrderByTdDt(account: Account, start : LocalDateTime, end : LocalDateTime): List<TradeHistory>?
    fun findAllDistinctByAccountAcNoOrderByTdDtDesc(acNo: String): List<TradeHistory>
    fun findAllByAccountAcNoAndTdTypeOrderByTdDtDesc(acNo: String, type: Int): List<TradeHistory>?
    fun findAllByTdTgAc(tdTgAc : String) : List<TradeHistory>?
}
