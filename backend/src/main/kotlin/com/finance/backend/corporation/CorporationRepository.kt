package com.finance.backend.corporation

import org.springframework.data.jpa.repository.JpaRepository

interface CorporationRepository: JpaRepository<Corporation, Long> {
    fun findByCpCode(cpCode: Long): Corporation?
    fun findTop16ByOrderByCpCode(): List<Corporation>
    fun findTop25ByOrderByCpCodeDesc(): List<Corporation>

    fun findByCpName(cpName: String): Corporation?
}