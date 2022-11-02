package com.finance.android.domain.repository

import com.finance.android.domain.service.StockService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockService: StockService
) : StockRepository
