package com.finance.backend.auth.response

import com.finance.backend.user.User
import java.util.*

class LoginDao (
        username : String,
        userId : UUID,
        accessToken: String,
        refreshToken: String
        ) {
    var username : String = username
    var userId : UUID = userId
    var accessToken : String = accessToken
    var refreshToken : String = refreshToken
}