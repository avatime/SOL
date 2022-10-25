package com.finance.backend.point

import com.finance.backend.point.request.RewardDto
import com.finance.backend.point.response.RewardDao
import com.finance.backend.user.User

interface RewardService {
    fun getAllPoint(accessToken : String) : List<RewardDao>
    fun getAccumulatedPoint(accessToken : String) : List<RewardDao>
    fun getUsedPoint(accessToken : String) : List<RewardDao>
    fun changeToCash(accessToken: String, rewardDto: RewardDto)
    fun accumulatePoint(user:User, point: Int, name : String)
    fun usePoint(user: User, point: Int, name : String)
}