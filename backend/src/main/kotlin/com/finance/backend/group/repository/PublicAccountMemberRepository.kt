package com.finance.backend.group.repository

import com.finance.backend.group.entity.PublicAccount
import com.finance.backend.group.entity.PublicAccountMember
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.function.LongToDoubleFunction

interface PublicAccountMemberRepository : JpaRepository<PublicAccountMember, Long> {
    fun findAllByUserAndPublicAccount_PaStatus(user : User, status : Int) : List<PublicAccountMember>?
    fun existsByUserAndPublicAccountAndType(user: User, publicAccount: PublicAccount, type : String) : Boolean
    fun existsByUserAndPublicAccountId(user: User, publicAccountId: Long) : Boolean
    fun findAllByPublicAccountId(publicAccountId: Long) : List<PublicAccountMember>?
    fun findByUserAndPublicAccountId(user: User, publicAccountId: Long) : PublicAccountMember?
}