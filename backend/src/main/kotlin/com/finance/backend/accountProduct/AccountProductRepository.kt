package com.finance.backend.accountProduct

import org.springframework.data.jpa.repository.JpaRepository

interface AccountProductRepository: JpaRepository<AccountProduct, Long> {
    fun findByCpCode(cpCode: Long): AccountProduct
}