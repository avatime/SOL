package com.finance.backend.daily.repository

import com.finance.backend.daily.entity.Walk
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface WalkRepository : JpaRepository<Walk, Long> {
    fun findAllByUserAndWalkDateBetweenOrderByWalkDateAsc(user: User, startDateTime : LocalDateTime, endDateTime : LocalDateTime) : List<Walk>?
    fun findByUserAndWalkDateBetween(user: User, startDateTime : LocalDateTime, endDateTime : LocalDateTime) : Walk?
}