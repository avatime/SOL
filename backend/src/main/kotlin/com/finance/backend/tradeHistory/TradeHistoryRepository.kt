package com.finance.backend.tradeHistory

import com.finance.backend.tradeHistory.TradeHistory
import org.apache.ibatis.annotations.Param
import org.hibernate.sql.Select
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Date

interface TradeHistoryRepository: JpaRepository<TradeHistory, Long> {
    fun findAllByAccountAcNo(acNo: String): List<TradeHistory>

    @Modifying
    @Query("SELECT * FROM TradeHistory th WHERE th.ac_no == :acNo And th.td_dt BETWEEN DATE_ADD(NOW(), INTERVAL -3 MONTH ) AND NOW()", nativeQuery = true)
    fun getRecentTrade(@Param(value = "account") acNo: String): List<TradeHistory>

    @Modifying
    @Query("SELECT th.tdTgAc, max(th.tdDt) from TradeHistory th group by :acNo order by :acNo")
    fun getAllByAcNo(@Param(value = "account") acNo: String): List<Pair<String, Date>>

    fun findAllDistinctByAccountAcNoOrderByTdDtDesc(acNo: String): List<TradeHistory>
    fun findAllByAccountAcNoAndTdTypeOrderByTdDtDesc(acNo: String, type: Int): List<TradeHistory>
}
