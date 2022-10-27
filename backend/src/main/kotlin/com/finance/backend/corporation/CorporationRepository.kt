package com.finance.backend.corporation

import org.springframework.data.jpa.repository.JpaRepository

interface CorporationRepository: JpaRepository<Corporation, Long> {
    fun findTop16ByOrderByCpCode(): List<Corporation>
    fun findTop25ByOrderByCpCodeDesc(): List<Corporation>
}