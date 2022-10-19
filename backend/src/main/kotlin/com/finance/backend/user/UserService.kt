package com.finance.backend.user

import com.finance.backend.auth.LoginDto
import com.finance.backend.auth.LoginDao
import com.finance.backend.auth.SignupDto
import com.finance.backend.auth.TokenDto

interface UserService {
    fun saveUser(signupDto: SignupDto) : LoginDao
    fun login(loginDto: LoginDto) : LoginDao?
    fun logout(token: String) : Boolean
}