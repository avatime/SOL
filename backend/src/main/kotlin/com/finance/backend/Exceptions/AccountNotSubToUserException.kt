package com.finance.backend.Exceptions

class AccountNotSubToUserException : Exception() {
    override val message: String?
        get() = "해당 유저의 계좌가 아닙니다."
}