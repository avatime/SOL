package com.finance.backend.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, String> {
    fun findById(id : UUID) : Optional<User>
    fun existsByNameAndPhoneAndBirth(name : String, phone : String, birth : Date) : Boolean
    fun existsByPhone(phone : String) : Boolean
}