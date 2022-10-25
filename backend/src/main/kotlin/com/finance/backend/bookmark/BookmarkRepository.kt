package com.finance.backend.bookmark

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BookmarkRepository: JpaRepository<Bookmark, Long> {
    fun existsByUserIdAndAcNo(userId: UUID, acNo: String) : Boolean
    fun findByUserIdAndAcNo(userId: UUID, acNo: String) : Bookmark
    fun findByUserId(userId: UUID) : List<Bookmark>
}