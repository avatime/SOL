package com.finance.backend.attendance

import com.finance.backend.attendance.response.AttendanceDao
import com.finance.backend.auth.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.user.Exceptions.InvalidUserException
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

@Service("DailyService")
@RequiredArgsConstructor
class DailyServiceImpl(
        private val userRepository: UserRepository,
        private val attendanceRepository: AttendanceRepository,
        private val jwtUtils: JwtUtils
) : DailyService {
    override fun check(accessToken: String) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            attendanceRepository.save(Attendance(user))
        } else throw Exception()
    }

    override fun getAttendance(accessToken: String, year: Int, month : Int) : List<AttendanceDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.plusMonths(1).minusDays(1)
            val dayList: List<Attendance> = attendanceRepository.findAllByUserAndAttDateBetween(user, startDate, endDate).get()
            return List(startDate.until(endDate, ChronoUnit.DAYS).toInt()) { i -> AttendanceDao(startDate.plusDays(i.toLong()), isAttend(dayList, startDate.plusDays(i.toLong()))) }
        } else throw Exception()
    }

    fun isAttend(dayList : List<Attendance>, date : LocalDate) : Boolean {
        for(attend in dayList) {
            if(date.isEqual(attend.attDate.toLocalDate())) {
                return true
            } else if(date.isBefore(attend.attDate.toLocalDate())) break
        }
        return false
    }
}