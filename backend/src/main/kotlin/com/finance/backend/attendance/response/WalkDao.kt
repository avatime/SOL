package com.finance.backend.attendance.response

import java.time.LocalDate

data class WalkDao(
        val day : LocalDate,
        val success : Boolean,
        val walk : Int
)
