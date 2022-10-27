package com.finance.backend.cardProduct

import org.springframework.data.jpa.repository.JpaRepository

interface CardProductRepository: JpaRepository<CardProduct, Long> {

}