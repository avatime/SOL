package com.finance.backend.point

import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PointRepository : JpaRepository<Point, Long> {
    fun findAllByUser(user : User) : Optional<List<Point>>
    fun findAllByUserAndPointLessThan(user: User, point : Int) : Optional<List<Point>>
    fun findAllByUserAndPointGreaterThan(user : User, point : Int) : Optional<List<Point>>
}