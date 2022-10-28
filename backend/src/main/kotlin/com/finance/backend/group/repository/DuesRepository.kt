package com.finance.backend.group.repository

import com.finance.backend.group.entity.Dues
import org.springframework.data.jpa.repository.JpaRepository

interface DuesRepository : JpaRepository<Dues, Long> {
    fun findAllByPublicAccountIdAndStatus(publicAccountId : Long, status : Int) : List<Dues>?
    fun findByIdAndStatus(id:Long, status: Int) : Dues?
}