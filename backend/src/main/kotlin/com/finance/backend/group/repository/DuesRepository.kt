package com.finance.backend.group.repository

import org.springframework.data.jpa.repository.JpaRepository

interface DuesRepository : JpaRepository<DuesRepository, Long> {
}