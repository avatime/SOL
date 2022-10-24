package com.finance.backend.point

import com.finance.backend.auth.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.point.response.RewardDao
import com.finance.backend.user.Exceptions.InvalidUserException
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.*

@Service("RewardService")
@RequiredArgsConstructor
class RewardServiceImpl(
        private val rewardRepository: RewardRepository,
        private val userRepository: UserRepository,
        private val jwtUtils: JwtUtils
) : RewardService {
    override fun getAllPoint(accessToken: String): List<RewardDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Reward> = rewardRepository.findAllByUser(user).orElseGet(null)
            return List(pointList.size) {i -> pointList[i].toEntity() }
        } else throw Exception()
    }

    override fun getAccumulatedPoint(accessToken: String): List<RewardDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Reward> = rewardRepository.findAllByUserAndPointGreaterThan(user, 0).orElseGet(null)
            return List(pointList.size) {i -> pointList[i].toEntity()}
        } else throw Exception()
    }

    override fun getUsedPoint(accessToken: String): List<RewardDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Reward> = rewardRepository.findAllByUserAndPointLessThan(user, 0).orElseGet(null)
            return List(pointList.size) {i -> pointList[i].toEntity()}
        } else throw Exception()
    }

    override fun accumulatePoint(user: User, point: Int, name : String) {
        rewardRepository.save(Reward(user, point, name))
        user.addPoint(point)
        userRepository.save(user)
    }

    override fun usePoint(user: User, point: Int, name : String) {
        rewardRepository.save(Reward(user, point * -1, name))
        user.addPoint(point * -1)
        userRepository.save(user)
    }
}