package com.finance.backend.bank

import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository : JpaRepository<Account, String> {
    fun findByUserId(userId : UUID) : List<Account>
    fun existsByAcNoAndUser(acNo : String, user : User) : Boolean
}