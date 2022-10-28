package com.finance.backend.Exceptions

class DuesNotExistsException : Exception() {
    override val message: String?
        get() = "존재하지 않는 회비입니다."
}