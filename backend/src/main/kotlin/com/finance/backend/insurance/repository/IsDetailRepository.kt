package com.finance.backend.insurance.repository

import com.finance.backend.insurance.entity.IsDetail
import com.finance.backend.insurance.entity.IsProduct
import org.springframework.data.jpa.repository.JpaRepository

interface IsDetailRepository : JpaRepository<IsDetail, Long> {
    fun findAllByIsPd(isPd : IsProduct) : List<IsDetail>
}