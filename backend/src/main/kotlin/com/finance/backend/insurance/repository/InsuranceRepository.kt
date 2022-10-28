package com.finance.backend.insurance.repository

import com.finance.backend.insurance.entity.Insurance
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InsuranceRepository : JpaRepository<Insurance, Long> {
    fun findByIdAndUserAndType(id : Long, user : User, type : Int) : Insurance?
    fun findAllByUserAndType(user : User, type : Int) : List<Insurance>
    fun findAllByUserIdAndIsRegAndType(userid: UUID, isReg : Boolean, type : Int) : List<Insurance>
}