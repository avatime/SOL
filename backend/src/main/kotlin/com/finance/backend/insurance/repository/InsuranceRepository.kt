package com.finance.backend.insurance.repository

import com.finance.backend.insurance.entity.Insurance
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface InsuranceRepository : JpaRepository<Insurance, Long> {
    fun findByIdAndUser(id : Long, user : User) : Insurance?
    fun findAllByUser(user : User) : List<Insurance>
    fun findAllByUserIdAndIsReg(userId: UUID, isReg : Boolean) : List<Insurance>
}