package com.finance.backend.auth.request

import com.finance.backend.user.User
import java.text.SimpleDateFormat
import java.util.*

data class SignupDto(
        var username: String,
        var password: String,
        var type: String,
        var phone : String,
        var birth : String,
        var sex : Int
) {
    fun toEntity(): User = User(this.username, this.password, this.phone, SimpleDateFormat("yyyy.MM.dd").parse(this.birth), this.sex, "회원")
}
