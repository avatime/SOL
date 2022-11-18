package com.finance.backend.Exceptions

class DuplicatedPhoneNumberException : Exception() {
    override val message: String?
        get() = "이미 가입된 전화번호입니다."
}