package com.finance.backend.user

class UserDao(
        username : String,
        profile_name: String,
        profile_url : String,
        point : Long
) {
    val username : String = username
    val profile_name : String = profile_name
    val profile_url : String = profile_url
    val point : Long = point
}