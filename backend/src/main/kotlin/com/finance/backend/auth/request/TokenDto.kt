package com.finance.backend.auth.request

import com.fasterxml.jackson.annotation.JsonProperty

class TokenDto(token : String) {
    var token : String = token
}