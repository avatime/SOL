package com.finance.backend.attendance

import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface AttendanceRepository : JpaRepository<Attendance, Long> {
    fun findAllByUserAndAttDateBetween(user: User, startDateTime : LocalDateTime, endDateTime : LocalDateTime) : Optional<List<Attendance>>
    fun findByUserAndAttDateBetween(user: User, startDateTime : LocalDateTime, endDateTime : LocalDateTime) : Optional<Attendance>
}