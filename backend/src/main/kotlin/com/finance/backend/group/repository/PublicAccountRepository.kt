package com.finance.backend.group.repository

import com.finance.backend.group.entity.PublicAccount
import org.springframework.data.jpa.repository.JpaRepository

interface PublicAccountRepository : JpaRepository<PublicAccount, Long> {
}