package com.finance.android.domain.service

import com.finance.android.domain.dto.request.CheckUserRequestDto
import com.finance.android.domain.dto.request.LoginRequestDto
import com.finance.android.domain.dto.request.ReLoginRequestDto
import com.finance.android.domain.dto.request.SignupRequestDto
import com.finance.android.domain.dto.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("auth/signup/check")
    suspend fun checkUser(@Body checkUserRequestDto: CheckUserRequestDto)

    @POST("auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): LoginResponseDto

    @POST("auth/relogin")
    suspend fun reLogin(@Body reLoginRequestDto: ReLoginRequestDto): LoginResponseDto

    @POST("auth/signup")
    suspend fun signup(@Body signupRequestDto: SignupRequestDto): LoginResponseDto
}
