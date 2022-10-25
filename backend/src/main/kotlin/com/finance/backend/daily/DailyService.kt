package com.finance.backend.daily

import com.finance.backend.daily.response.AttendanceDao
import com.finance.backend.daily.response.WalkDao

interface DailyService {
    fun check(accessToken : String)
    fun getAttendance(accessToken: String, year: Int, month : Int) : List<AttendanceDao>

    fun walk(accessToken: String, walkNum : Int)
//    fun walk(accessToken: String)
    fun getWalk(accessToken: String, year: Int, month: Int) : List<WalkDao>
}