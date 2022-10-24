package com.finance.backend.attendance.response

import java.time.LocalDate

data class AttendanceDao(
        val day : LocalDate,
        val attendance : Boolean
)
