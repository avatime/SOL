package com.finance.backend.attendance

import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface AttendanceRepository : JpaRepository<Attendance, Long> {
    fun findByAttDateBetween()
}