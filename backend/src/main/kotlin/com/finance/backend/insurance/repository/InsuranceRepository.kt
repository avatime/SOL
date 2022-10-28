package com.finance.backend.insurance.repository

import com.finance.backend.insurance.entity.Insurance
import org.springframework.data.jpa.repository.JpaRepository

interface InsuranceRepository : JpaRepository<Insurance, Long> {
}