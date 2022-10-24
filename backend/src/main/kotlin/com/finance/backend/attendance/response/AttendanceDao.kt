package com.finance.backend.attendance.response

import java.time.LocalDate
import java.util.Date

data class AttendanceDao(
        val day : LocalDate,
        val attendance : Boolean
)
