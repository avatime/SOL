package com.finance.backend.auth

import com.fasterxml.jackson.annotation.JsonProperty

class LoginDTO(
        password : String,
        refreshToken: String
) {
    var password : String = password

    @JsonProperty("refresh_token")
    var refreshToken : String = refreshToken
}