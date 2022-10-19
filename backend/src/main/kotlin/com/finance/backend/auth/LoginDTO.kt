package com.finance.backend.auth

class LoginDTO(
        username : String,
        password : String,
        accessToken: String,
        refreshToken: String
) {
    var username : String = username
    var password : String = password
    var token : Token = Token(accessToken, refreshToken)
}