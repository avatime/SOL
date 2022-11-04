package com.finance.backend.bookmark

import com.finance.backend.tradeHistory.TradeHistory
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface BookmarkRepository: JpaRepository<Bookmark, Long> {
    fun existsByUserIdAndAcNo(userId: UUID, acNo: String) : Boolean
    fun existsByUserIdAndAcNoAndBkStatus(userId: UUID, acNo: String, bkStatus: Boolean) : Boolean
    fun findByUserIdAndAcNo(userId: UUID, acNo: String) : Bookmark
    fun findByUserId(userId: UUID) : List<Bookmark>?

}