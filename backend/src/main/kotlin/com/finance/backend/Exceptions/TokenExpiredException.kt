package com.finance.backend.Exceptions

class TokenExpiredException : Exception() {
    override val message: String?
        get() = "토큰이 만료되어 재로그인이 필요합니다."
}