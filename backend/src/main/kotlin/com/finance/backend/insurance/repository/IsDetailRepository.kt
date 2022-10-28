package com.finance.backend.insurance.repository

import com.finance.backend.insurance.entity.IsDetail
import org.springframework.data.jpa.repository.JpaRepository

interface IsDetailRepository : JpaRepository<IsDetail, Long> {
}