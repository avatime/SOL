package com.finance.backend.insurance.repository

import com.finance.backend.insurance.entity.IsProduct
import org.springframework.data.jpa.repository.JpaRepository

interface IsProductRepository : JpaRepository<IsProduct, Long> {
}