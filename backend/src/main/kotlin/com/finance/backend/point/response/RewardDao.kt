package com.finance.backend.point.response

import java.time.LocalDateTime

data class RewardDao(val day : LocalDateTime, val point : Int, val name : String)
