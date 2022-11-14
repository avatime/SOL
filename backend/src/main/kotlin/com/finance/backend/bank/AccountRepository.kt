package com.finance.backend.bank

import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository : JpaRepository<Account, String> {
    fun findByUserId(userId : UUID) : List<Account>?
    fun findByAcNo(acNo : String) : Account?
    fun existsByAcNoAndUser(acNo : String, user : User) : Boolean
    fun findByAcNoAndAcCpCode(acNo: String, acCpCode: Long): Account?
    fun findByAcNoAndUser(acNo: String, user : User) : Account?
    fun findByUserIdAndAcTypeAndAcReg(userId: UUID, acType: Int, acReg: Boolean): List<Account>?
    fun findByUserIdAndAcType(userId: UUID, acType: Int): List<Account>?
    fun findByUserIdAndAcReg(userId: UUID, acReg: Boolean) : List<Account>?
}