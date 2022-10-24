package com.finance.backend.point

import com.finance.backend.point.response.PointDao
import com.finance.backend.user.User

interface PointService {
    fun getAllPoint(accessToken : String) : List<PointDao>
    fun getAccumulatedPoint(accessToken : String) : List<PointDao>
    fun getUsedPoint(accessToken : String) : List<PointDao>
    fun accumulatePoint(user:User, point: Int, name : String)
    fun usePoint(user: User, point: Int, name : String)
}