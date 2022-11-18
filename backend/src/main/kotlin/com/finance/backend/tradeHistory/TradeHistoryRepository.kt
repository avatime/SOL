package com.finance.backend.tradeHistory

import com.finance.backend.bank.Account
import com.finance.backend.tradeHistory.TradeHistory
import com.finance.backend.user.User
import org.apache.ibatis.annotations.Param
import org.hibernate.sql.Select
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

interface TradeHistoryRepository: JpaRepository<TradeHistory, Long> {
    fun findAllByAccountAcNoOrderByTdDtDesc(acNo: String): List<TradeHistory>?
    fun findAllByAccountAcNoAndTdTypeAndTdDtBetween(acNo: String, tdType: Int, start : LocalDateTime, end : LocalDateTime): List<TradeHistory>?
    fun findAllDistinctByAccountAcNoOrderByTdDtDesc(acNo: String): List<TradeHistory>
    fun findAllByAccountAcNoAndTdTypeOrderByTdDtDesc(acNo: String, type: Int): List<TradeHistory>?
    fun findAllByTdTgAc(tdTgAc : String) : List<TradeHistory>?
    fun findTopByAccountAcNoAndTdTgAcAndTdTypeOrderByTdDtDesc(acNo: String, tdTgAc: String, tdType: Int) : TradeHistory
    @Query(value = "select * from trade_history th join bookmark bk on th.td_tg_ac = bk.ac_no where bk_status = 1 and (bk.ac_no, th.td_dt) in (select td_tg_ac, td_dt from trade_history where (td_tg_ac, td_dt) in (select td_tg_ac, max(td_dt) from trade_history t join account a on t.ac_no = a.ac_no where a.user_id = :userId group by td_tg_ac));", nativeQuery = true)
    fun getMyBookmarkTradeHistoriesByUserId(@Param("userId") userId : UUID): List<TradeHistory>?
    @Query(value = "select * from trade_history th join bookmark bk on th.td_tg_ac = bk.ac_no where bk_status = 1 and (bk.ac_no, th.td_dt) in (select td_tg_ac, td_dt from trade_history where (td_tg_ac, td_dt) in (select td_tg_ac, max(td_dt) from trade_history t join account a on t.ac_no = a.ac_no where a.user_id = :userId and td_type = 2 group by td_tg_ac));", nativeQuery = true)
    fun getBookMarkTradeHistoriesByUserId(@Param("userId") userId : UUID) : List<TradeHistory>?
    @Query(value= "select * from trade_history where (td_tg_ac, td_dt) in (select td_tg_ac, max(td_dt) from trade_history t join account a on t.ac_no = a.ac_no where a.user_id = :userId and td_type = 2 group by td_tg_ac)", nativeQuery = true)
    fun getTradeHistoriesByUserId(@Param("userId") userId : UUID) : List<TradeHistory>?
}
