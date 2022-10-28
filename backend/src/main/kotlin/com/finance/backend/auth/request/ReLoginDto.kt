package com.finance.backend.auth.request

import com.fasterxml.jackson.annotation.JsonProperty

class ReLoginDto(
        password : String,
        phone: String
) {
    var password : String = password

    var phone : String = phone
}