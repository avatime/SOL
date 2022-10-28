package com.finance.backend.card

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CardRepository : JpaRepository<Card, String> {
    fun findAllByUserId(UUID: UUID): List<Card>
}