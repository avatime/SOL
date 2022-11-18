package com.finance.backend.daily.repository

import com.finance.backend.daily.entity.Attendance
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface AttendanceRepository : JpaRepository<Attendance, Long> {
    fun findAllByUserAndAttDateBetween(user: User, startDateTime : LocalDateTime, endDateTime : LocalDateTime) : List<Attendance>?
    fun findByUserAndAttDateBetween(user: User, startDateTime : LocalDateTime, endDateTime : LocalDateTime) : Attendance?
}