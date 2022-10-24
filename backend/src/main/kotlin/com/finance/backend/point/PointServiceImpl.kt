package com.finance.backend.point

import com.finance.backend.auth.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.point.response.PointDao
import com.finance.backend.user.Exceptions.InvalidUserException
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.*

@Service("PointService")
@RequiredArgsConstructor
class PointServiceImpl(
        private val pointRepository: PointRepository,
        private val userRepository: UserRepository,
        private val jwtUtils: JwtUtils
) : PointService {
    override fun getAllPoint(accessToken: String): List<PointDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Point> = pointRepository.findAllByUser(user).orElseGet(null)
            return List(pointList.size) {i -> pointList[i].toEntity() }
        } else throw Exception()
    }

    override fun getAccumulatedPoint(accessToken: String): List<PointDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Point> = pointRepository.findAllByUserAndPointGreaterThan(user, 0).orElseGet(null)
            return List(pointList.size) {i -> pointList[i].toEntity()}
        } else throw Exception()
    }

    override fun getUsedPoint(accessToken: String): List<PointDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Point> = pointRepository.findAllByUserAndPointLessThan(user, 0).orElseGet(null)
            return List(pointList.size) {i -> pointList[i].toEntity()}
        } else throw Exception()
    }

    override fun accumulatePoint(user: User, point: Int, name : String) {
        pointRepository.save(Point(user, point, name))
        user.addPoint(point)
        userRepository.save(user)
    }

    override fun usePoint(user: User, point: Int, name : String) {
        pointRepository.save(Point(user, point * -1, name))
        user.addPoint(point * -1)
        userRepository.save(user)
    }
}