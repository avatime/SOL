package com.finance.backend.Exceptions

class NoPhoneTokenException  : Exception(){
    override val message: String?
        get() = "존재하지 않는 비회원 폰 거래 내역입니다."
}