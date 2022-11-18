package com.finance.backend.Exceptions

class NoCorporationException: Exception() {
    override val message: String?
        get() = "존재하지 않는 회사입니다."
}