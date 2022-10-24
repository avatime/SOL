package com.finance.backend.attendance

import com.finance.backend.attendance.response.AttendanceDao
import com.finance.backend.attendance.response.WalkDao
import javax.accessibility.AccessibleSelection

interface DailyService {
    fun check(accessToken : String)
    fun getAttendance(accessToken: String, year: Int, month : Int) : List<AttendanceDao>

    fun walk(accessToken: String, walkNum : Int)
//    fun walk(accessToken: String)
    fun getWalk(accessToken: String, year: Int, month: Int) : List<WalkDao>
}