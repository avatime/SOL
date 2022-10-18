package com.finance.backend.user

import com.finance.backend.user.User

data class UserDto(
        var username: String,
        var password: String
) {
    fun toEntity(): User = User(this.username, this.password)
}
