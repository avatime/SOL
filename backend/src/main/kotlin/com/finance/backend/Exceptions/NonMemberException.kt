package com.finance.backend.Exceptions

class NonMemberException : Exception() {
    override val message: String?
        get() = "비회원입니다."
}