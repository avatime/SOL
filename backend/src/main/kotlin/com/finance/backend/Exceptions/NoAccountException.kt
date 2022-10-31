package com.finance.backend.Exceptions

class NoAccountException : Exception() {
    override val message: String?
        get() = "존재하지 않는 계좌입니다."
}