package com.finance.backend.Exceptions

class NoCardException : Exception() {
    override val message: String?
        get() = "존재하지 않는 카드입니다."
}
