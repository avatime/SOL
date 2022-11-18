package com.finance.backend.auth.request

import com.finance.backend.user.User
import java.text.SimpleDateFormat
import java.util.*

data class SignupCheckDto(
        var username: String,
        var phone : String,
        var birth : String,
) {
}
