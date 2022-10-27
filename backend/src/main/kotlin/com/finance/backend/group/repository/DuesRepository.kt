package com.finance.backend.group.repository

import com.finance.backend.group.entity.Dues
import org.springframework.data.jpa.repository.JpaRepository

interface DuesRepository : JpaRepository<Dues, Long> {
    fun findAllByPublicAccountId(publicAccountId : Long) : List<Dues>?
}