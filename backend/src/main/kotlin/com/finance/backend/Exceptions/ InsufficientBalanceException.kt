package com.finance.backend.Exceptions

class InsufficientBalanceException : Exception() {
    override val message: String?
        get() = "Insufficient balance!!!"
}