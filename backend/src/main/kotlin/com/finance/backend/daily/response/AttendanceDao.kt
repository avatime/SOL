package com.finance.backend.daily.response

import java.time.LocalDate

data class AttendanceDao(
        val day : LocalDate,
        val attendance : Boolean
)
