package com.finance.backend.user

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
//    fun findById(id : UUID) : Optional<User>
    fun findById(id : String) : User?
    fun findByPhone(phone : String) : User?
    fun existsByNameAndPhoneAndBirthBetween(name : String, phone : String, startDateTime : Date, endDateTime: Date) : Boolean
    fun existsByPhoneAndType(phone : String, type : String) : Boolean
    fun existsByPhone(phone: String) : Boolean
}