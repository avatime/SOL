package com.finance.backend.user

import com.finance.backend.auth.LoginDTO
import com.finance.backend.auth.LoginDao
import com.finance.backend.auth.SignupDto

interface UserService {
    fun saveUser(signupDto: SignupDto) : LoginDao
    fun login(loginDto: LoginDTO) : LoginDao?
}