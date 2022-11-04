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
            rewardService.accumulatePoint(user, 50, "출석 체크")
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
     * walk를 한번에 더하는 방식
     */
    override fun walk(accessToken: String, walkNum : Int) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val walk : Walk = walkRepository.findByUserAndWalkDateBetween(user, now().atStartOfDay(), now().atTime(LocalTime.MAX))?: Walk(user)
            walk.walk(walkNum)
            walkRepository.save(walk)
        } else throw Exception()
    }

    /**
     * walk 부를때마다 1 더해지는 방식
     */
//    override fun walk(accessToken: String) {
//        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
//            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
//            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
//            val walk : Walk = walkRepository.findByUserAndWalkDateBetween(user, now().atStartOfDay(), now().atTime(LocalTime.MAX))?:Walk(user)
//            walk.walk(walkNum)
//            walkRepository.save(walk)
//        } else throw Exception()
//    }

    override fun getWalk(accessToken: String, year: Int, month: Int)  : List<WalkDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.plusMonths(1)
            val dayList: List<Walk> = walkRepository.findAllByUserAndWalkDateBetween(user, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX))?: emptyList()
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

            factory.setConnectTimeout(5000)
            factory.setReadTimeout(5000)

            val restTemplate = RestTemplate(factory)
            //restTemplate은 Rest방식 api를 호출할 수 있는 spring 내장 클래스이다.
            //json, xml 응답을 모두 받을 수 있다.

            //header 클래스를 정의해 주고, url을 정의해 주고 exchange method로 api를 호출한다.
            val header = HttpHeaders()
            //header.contentType= MediaType.parseMediaType("application/json")

            val entity = HttpEntity<Map<String, Any>>(header)
            val url = "https://k7a403.p.ssafy.io/data/v1/finance/scheduler"
            val uri : UriComponents
                    = UriComponentsBuilder.fromHttpUrl(url).build()

            //api를 호출하여 데이터를 MAP타입으로 전달 받는다.
            val resultMap : ResponseEntity<Map<*, *>>
                    = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map::class.java)

            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            resultMap.body?.let { result.put("body", it) };
            //result.put("body", resultMap.getBody())로 넣을 수 없었다. null 가능성 때문인 것 같다.

            //데이터를 string형태로 파싱해줌
            val mapper = ObjectMapper()
            jsonInString = mapper.writeValueAsString(resultMap.getBody());

        } catch (e: Exception){
            when(e) {
                is HttpClientErrorException, is HttpServerErrorException -> {
                    result.put("statusCode", e.hashCode()); //여기랑
                    result.put("body", e.stackTraceToString()); //여기는 kotlin에서 오류가 났다. 그래서 함수를 그냥 따옴표로 감싸버림.. 확인 필요
                    System.out.println("error!");
                    System.out.println(e.toString());
                }else -> {
                result.put("statusCode", "999");
                result.put("body", "excpetion 오류");
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
            if(date.isEqual(listDate.walkDate.toLocalDate())) {
                return if(listDate.walk >= goal) WalkDao(date, true, listDate.walk)
                else WalkDao(date, false, listDate.walk)
            } else if(date.isBefore(listDate.walkDate.toLocalDate())) break
        }
        return WalkDao(date, false, 0)
    }
}