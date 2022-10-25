package com.finance.backend.group.repository

import com.finance.backend.group.entity.PublicAccountMember
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.function.LongToDoubleFunction

interface PublicAccountMemberRepository : JpaRepository<PublicAccountMember, Long> {
    fun findAllByUserAndAndPublicAccount_PaStatus(user : User, status : Int) : List<PublicAccountMember>?
}