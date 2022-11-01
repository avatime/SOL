package com.finance.backend.Exceptions

class NoTradeHistoryException: Exception() {
    override val message: String?
        get() = "거래 내역이 없습니다."
}