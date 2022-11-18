package com.finance.backend.group.repository

import com.finance.backend.group.entity.Dues
import com.finance.backend.group.entity.UserDuesRelation
import com.finance.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDuesRelationRepository : JpaRepository<UserDuesRelation, Long> {
    fun findByUserAndDues(user : User, due : Dues) : UserDuesRelation?
    fun existsByUserAndDues(user : User, due : Dues) : Boolean
    fun findByUserAndDuesId(user : User, duesId : Long) : UserDuesRelation?
    fun findByUserAndId(user: User, id : Long) : UserDuesRelation?
    fun findAllByDues(due: Dues) : List<UserDuesRelation>?
    fun countByDuesAndStatus(due: Dues, status : Boolean) : Int
    fun countByDues(due: Dues) : Int
}