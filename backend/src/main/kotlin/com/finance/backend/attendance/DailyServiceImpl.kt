package com.finance.backend.attendance

import com.finance.backend.attendance.response.AttendanceDao
import com.finance.backend.attendance.response.WalkDao
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.user.Exceptions.InvalidUserException
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service("DailyService")
@RequiredArgsConstructor
class DailyServiceImpl(
        private val userRepository: UserRepository,
        private val attendanceRepository: AttendanceRepository,
        private val jwtUtils: JwtUtils
) : DailyService {
    private val goal : Int = 5000
    override fun check(accessToken: String) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            attendanceRepository.save(Attendance(user))
        } else throw Exception()
    }

    override fun getAttendance(accessToken: String, year: Int, month : Int) : List<AttendanceDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.plusMonths(1).minusDays(1)
            val dayList: List<Attendance> = attendanceRepository.findAllByUserAndAttDateBetween(user, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)).get()
            return List(startDate.until(endDate, ChronoUnit.DAYS).toInt()) { i -> AttendanceDao(startDate.plusDays(i.toLong()), isAttend(dayList, startDate.plusDays(i.toLong()))) }
        } else throw Exception()
    }

    /**
     * walk를 한번에 더하는 방식
     */
    override fun walk(accessToken: String, walkNum : Int) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val attend : Attendance = attendanceRepository.findByUserAndAttDateBetween(user, now().atStartOfDay(), now().atTime(LocalTime.MAX)).get()
            attend.walk(walkNum)
            attendanceRepository.save(attend)
        } else throw Exception()
    }

    /**
     * walk 부를때마다 1 더해지는 방식
     */
//    override fun walk(accessToken: String) {
//        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
//            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
//            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
//            val attend : Attendance = attendanceRepository.findByUserAndAttDateBetween(user, now().atStartOfDay(), now().atTime(LocalTime.MAX)).get()
//            attend.addWalk()
//            attendanceRepository.save(attend)
//        } else throw Exception()
//    }

    override fun getWalk(accessToken: String, year: Int, month: Int)  : List<WalkDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.plusMonths(1).minusDays(1)
            val dayList: List<Attendance> = attendanceRepository.findAllByUserAndAttDateBetween(user, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)).get()
            return List(startDate.until(endDate, ChronoUnit.DAYS).toInt()) { i -> isSuccess(dayList, startDate.plusDays(i.toLong())) }
        } else throw Exception()
    }

    fun isAttend(list : List<Attendance>, date : LocalDate) : Boolean {
        for(listDate in list) {
            if(date.isEqual(listDate.attDate.toLocalDate())) {
                return true
            } else if(date.isBefore(listDate.attDate.toLocalDate())) break
        }
        return false
    }

    fun isSuccess(list : List<Attendance>, date : LocalDate) : WalkDao {
        for(listDate in list) {
            if(date.isEqual(listDate.attDate.toLocalDate())) {
                return if(listDate.walk >= goal) WalkDao(date, true, listDate.walk)
                else WalkDao(date, false, listDate.walk)
            } else if(date.isBefore(listDate.attDate.toLocalDate())) break
        }
        return WalkDao(date, false, 0)
    }
}