package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.CheckUserRequestDto
import com.finance.android.domain.dto.request.LoginRequestDto
import com.finance.android.domain.dto.request.ReLoginRequestDto
import com.finance.android.domain.dto.request.SignupRequestDto
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface UserRepository {
    suspend fun loadPhoneCode(): Flow<Response<String>>
    suspend fun checkUser(@Body checkUserRequestDto: CheckUserRequestDto)
    suspend fun login(@Body loginRequestDto: LoginRequestDto): LoginResponseDto
    suspend fun reLogin(@Body reLoginRequestDto: ReLoginRequestDto): LoginResponseDto
    suspend fun signup(@Body signupRequestDto: SignupRequestDto): LoginResponseDto
}
