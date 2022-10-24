package com.finance.backend.point.response

import java.time.LocalDateTime
import java.util.Date

data class PointDao(val day : LocalDateTime, val point : Int, val name : String)
