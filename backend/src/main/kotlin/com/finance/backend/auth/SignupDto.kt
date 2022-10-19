package com.finance.backend.auth

import com.finance.backend.user.User

data class SignupDto(
        var username: String,
        var password: String,
        var type: String
) {
    fun toEntity(): User = User(this.username, this.password)
}
