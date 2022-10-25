package com.finance.backend.group.repository

import com.finance.backend.group.entity.UserDuesRelation
import org.springframework.data.jpa.repository.JpaRepository

interface UserDuesRelationRepository : JpaRepository<UserDuesRelation, Long> {
}