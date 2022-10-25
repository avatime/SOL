package com.finance.backend.daily.response

import java.time.LocalDate

data class WalkDao(
        val day : LocalDate,
        val success : Boolean,
        val walk : Int
)
