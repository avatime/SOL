package com.finance.backend.corporation

import org.springframework.data.jpa.repository.JpaRepository

interface CorporationRepository: JpaRepository<Corporation, Long> {
}