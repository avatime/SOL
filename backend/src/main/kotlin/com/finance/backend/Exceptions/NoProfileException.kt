package com.finance.backend.Exceptions

class NoProfileException : Exception() {
    override val message: String?
        get() = "존재하지 않는 프로필입니다."
}