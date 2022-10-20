package com.finance.backend.user.Exceptions

class InvalidUserException : Exception() {
    override val message: String?
        get() = "유저 정보가 없습니다."
}