package com.finance.backend.profile

import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByPfId(id : Long) : Profile?
}