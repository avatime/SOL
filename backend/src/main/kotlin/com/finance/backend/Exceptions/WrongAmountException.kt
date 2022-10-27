package com.finance.backend.Exceptions

class WrongAmountException : Exception() {
    override val message: String?
        get() = "금액이 올바르지 않습니다."
}