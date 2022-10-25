package com.finance.backend.tradeHistory

import com.finance.backend.tradeHistory.TradeHistory
import org.springframework.data.jpa.repository.JpaRepository

interface TradeHistoryRepository: JpaRepository<TradeHistory, Long> {
    fun findAllByAccountAcNo(acNo: String): List<TradeHistory>

    fun findAllByAccountAcNoAndTypeOrderByTdDtDesc(acNo: String, type: Int): List<TradeHistory>
}
