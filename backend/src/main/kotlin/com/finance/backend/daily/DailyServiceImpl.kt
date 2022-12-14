package com.finance.backend.daily

import com.fasterxml.jackson.databind.ObjectMapper
import com.finance.backend.daily.response.AttendanceDao
import com.finance.backend.daily.response.WalkDao
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.daily.entity.Attendance
import com.finance.backend.daily.entity.Walk
import com.finance.backend.daily.repository.AttendanceRepository
import com.finance.backend.daily.repository.WalkRepository
import com.finance.backend.daily.response.ProfileRes
import com.finance.backend.point.RewardService
import com.finance.backend.point.RewardServiceImpl
import com.finance.backend.profile.Profile
import com.finance.backend.profile.ProfileRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
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
        private val rewardService: RewardServiceImpl,
        private val walkRepository: WalkRepository,
        private val profileRepository: ProfileRepository,
        private val jwtUtils: JwtUtils
) : DailyService {
    private val goal : Int = 5000
    override fun check(accessToken: String) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            rewardService.accumulatePoint(user, 50, "?????? ??????")
            attendanceRepository.save(Attendance(user))
        } else throw Exception()
    }

    override fun getAttendance(accessToken: String, year: Int, month : Int) : List<AttendanceDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.plusMonths(1)
            val dayList: List<Attendance> = attendanceRepository.findAllByUserAndAttDateBetween(user, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX))?: emptyList()
            return List(startDate.until(endDate, ChronoUnit.DAYS).toInt()) { i -> AttendanceDao(startDate.plusDays(i.toLong()), isAttend(dayList, startDate.plusDays(i.toLong()))) }
        } else throw Exception()
    }

    /**
     * walk??? ????????? ????????? ??????
     */
    override fun walk(accessToken: String) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            rewardService.accumulatePoint(user, 500, "?????? ?????? ??????")
            walkRepository.save(Walk(user))
        } else throw Exception()
    }

    override fun getWalk(accessToken: String, year: Int, month: Int)  : List<WalkDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.plusMonths(1)
            val dayList: List<Walk> = walkRepository.findAllByUserAndWalkDateBetweenOrderByWalkDateAsc(user, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX))?: emptyList()
            return List(startDate.until(endDate, ChronoUnit.DAYS).toInt()) { i -> isSuccess(dayList, startDate.plusDays(i.toLong())) }
        } else throw Exception()
    }

    override fun getAllProfiles(accessToken: String): List<ProfileRes> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val list : List<Profile> = profileRepository.findAll()
            return List(list.size) {i -> list[i].toEntity()}
        } else throw Exception()
    }

    override fun sendRequest() : String {
        val result  = HashMap<String, Any>()
        var jsonInString = ""

        try {
            val factory = HttpComponentsClientHttpRequestFactory();

            factory.setConnectTimeout(50000)
            factory.setReadTimeout(50000)

            val restTemplate = RestTemplate(factory)
            //restTemplate??? Rest?????? api??? ????????? ??? ?????? spring ?????? ???????????????.
            //json, xml ????????? ?????? ?????? ??? ??????.

            //header ???????????? ????????? ??????, url??? ????????? ?????? exchange method??? api??? ????????????.
            val header = HttpHeaders()
            //header.contentType= MediaType.parseMediaType("application/json")

            val entity = HttpEntity<Map<String, Any>>(header)
            val url = "https://k7a403.p.ssafy.io/data/v1/finance/scheduler"
            val uri : UriComponents
                    = UriComponentsBuilder.fromHttpUrl(url).build()

            //api??? ???????????? ???????????? MAP???????????? ?????? ?????????.
            val resultMap : ResponseEntity<Map<*, *>>
                    = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map::class.java)

            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code??? ??????
            result.put("header", resultMap.getHeaders()); //?????? ?????? ??????
            resultMap.body?.let { result.put("body", it) };
            //result.put("body", resultMap.getBody())??? ?????? ??? ?????????. null ????????? ????????? ??? ??????.

            //???????????? string????????? ????????????
            val mapper = ObjectMapper()
            jsonInString = mapper.writeValueAsString(resultMap.getBody());

        } catch (e: Exception){
            when(e) {
                is HttpClientErrorException, is HttpServerErrorException -> {
                    result.put("statusCode", e.hashCode()); //?????????
                    result.put("body", e.stackTraceToString()); //????????? kotlin?????? ????????? ??????. ????????? ????????? ?????? ???????????? ????????????.. ?????? ??????
                    System.out.println("error!");
                    System.out.println(e.toString());
                }else -> {
                result.put("statusCode", "999");
                result.put("body", "excpetion ??????");
                System.out.println(e.toString());
            }
            }
        }

        return jsonInString;
    }

    fun isAttend(list : List<Attendance>, date : LocalDate) : Boolean {
        for(listDate in list) {
            if(date.isEqual(listDate.attDate.toLocalDate())) {
                return true
            } else if(date.isBefore(listDate.attDate.toLocalDate())) break
        }
        return false
    }

    fun isSuccess(list : List<Walk>, date : LocalDate) : WalkDao {
        for(listDate in list) {
            if(date.dayOfMonth == listDate.walkDate.toLocalDate().dayOfMonth) {
                return WalkDao(date, true)
            } else if(date.isBefore(listDate.walkDate.toLocalDate())) break
        }
        return WalkDao(date, false)
    }
}