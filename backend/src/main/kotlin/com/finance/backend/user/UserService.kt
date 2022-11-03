package com.finance.backend.user

import com.finance.backend.auth.request.LoginDto
import com.finance.backend.auth.request.ReLoginDto
import com.finance.backend.auth.request.SignupCheckDto
import com.finance.backend.auth.request.SignupDto
import com.finance.backend.auth.response.LoginDao
import com.finance.backend.user.response.UserDao

interface UserService {
    fun saveUser(signupDto: SignupDto) : LoginDao
    fun checkUser(signupDto: SignupCheckDto)
    fun login(loginDto: LoginDto) : LoginDao?
    fun reLogin(reLoginDto: ReLoginDto) : LoginDao?
    fun logout(token: String) : Boolean
    fun refresh(token: String) : String
    fun getUserInfo(token: String) : UserDao
    fun changeProfile(token: String, id : Long)
    fun check(phone : String) : Boolean
    fun checkAccount(token : String) : Boolean
}