package com.finance.backend.bank

import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository : JpaRepository<Account, String> {
    fun findByUserId(userId : UUID) : List<Account>
    fun existsByAcNoAndUser(acNo : String, user : User) : Boolean
    fun findByAcNoAndAcCpCode(acNo: String, acCpCode: Long): Account?
    fun findByUserIdAndAcType(userId: UUID, acType: Int): List<Account>?
}