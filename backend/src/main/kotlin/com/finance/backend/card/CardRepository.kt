package com.finance.backend.card

import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository : JpaRepository<Card, String> {
}