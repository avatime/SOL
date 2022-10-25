package com.finance.backend.bank

import org.springframework.data.jpa.repository.JpaRepository

interface TradeHistoryRepository: JpaRepository<TradeHistory, Long> {
    fun findAllByAccountAcNo(acNo: String) : List<TradeHistory>
}