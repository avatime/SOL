package com.finance.backend.Exceptions

class RemitFailedException : Exception() {
    override val message: String?
        get() = "잔액이 부족합니다."
}