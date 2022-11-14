package com.finance.backend.remit

import org.springframework.data.jpa.repository.JpaRepository

interface RemitAvailableRepository: JpaRepository<RemitAvailable, Long> {
}