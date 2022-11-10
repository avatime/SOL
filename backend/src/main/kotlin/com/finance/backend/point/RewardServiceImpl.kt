package com.finance.backend.point

import com.finance.backend.Exceptions.*
import com.finance.backend.bank.AccountRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.point.request.RewardDto
import com.finance.backend.point.response.RewardDao
import com.finance.backend.bank.Account
import com.finance.backend.tradeHistory.TradeHistory
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.lang.NullPointerException
import java.time.LocalDateTime
import java.util.*

@Service("RewardService")
@RequiredArgsConstructor
class RewardServiceImpl(
        private val rewardRepository: RewardRepository,
        private val userRepository: UserRepository,
        private val accountRepository: AccountRepository,
        private val tradeHistoryRepository: TradeHistoryRepository,
        private val jwtUtils: JwtUtils
) : RewardService {
    override fun getAllPoint(accessToken: String): List<RewardDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Reward> = rewardRepository.findAllByUser(user) ?: emptyList<Reward>()
            return List(pointList.size) {i -> pointList[i].toEntity() }
        } else throw Exception()
    }

    override fun getAccumulatedPoint(accessToken: String): List<RewardDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Reward> = rewardRepository.findAllByUserAndPointGreaterThan(user, 0)?: emptyList<Reward>()
            return List(pointList.size) {i -> pointList[i].toEntity()}
        } else throw Exception()
    }

    override fun getUsedPoint(accessToken: String): List<RewardDao> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val pointList : List<Reward> = rewardRepository.findAllByUserAndPointLessThan(user, 0)?: emptyList<Reward>()
            return List(pointList.size) {i -> pointList[i].toEntity()}
        } else throw Exception()
    }

    override fun changeToCash(accessToken: String, rewardDto: RewardDto) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            if(!accountRepository.existsByAcNoAndUser(rewardDto.account ?: user.account ?: throw NullPointerException(), user)) throw AccountNotSubToUserException()
            if(user.point < rewardDto.point) throw InsufficientBalanceException()
            val account : Account = accountRepository.findByAcNo(rewardDto.account?:user.account!!) ?: throw NoAccountException()
            account.deposit(rewardDto.point.toLong())
            val tradeHistory : TradeHistory = TradeHistory("포인트 전환", rewardDto.point.toLong(), LocalDateTime.now(), 1, "", null, "", "", account)
            usePoint(user, rewardDto.point, "포인트 전환")
            accountRepository.save(account)
            tradeHistoryRepository.save(tradeHistory)
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