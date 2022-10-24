package com.finance.backend.attendance

import com.finance.backend.attendance.response.AttendanceDao

interface DailyService {
    fun check(accessToken : String)
    fun getAttendance(accessToken: String, year: Int, month : Int) : List<AttendanceDao>
}