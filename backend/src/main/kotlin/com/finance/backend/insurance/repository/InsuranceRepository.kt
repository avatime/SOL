package com.finance.backend.insurance.repository

import com.finance.backend.insurance.entity.Insurance
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InsuranceRepository : JpaRepository<Insurance, Long> {
    fun findByIdAndUserAndIsStatus(id : Long, user : User, type : Int) : Insurance?
    fun findAllByUserAndIsStatus(user : User, type : Int) : List<Insurance>
    fun findAllByUserIdAndIsRegAndIsStatus(userid: UUID, isReg : Boolean, type : Int) : List<Insurance>
}