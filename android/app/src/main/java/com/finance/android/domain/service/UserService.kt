package com.finance.android.domain.service

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("${Const.API_PATH}/auth/signup/check")
    suspend fun checkUser(@Body checkUserRequestDto: CheckUserRequestDto)

    @POST("${Const.API_PATH}/auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): LoginResponseDto

    @POST("${Const.API_PATH}/auth/relogin")
    suspend fun reLogin(@Body reLoginRequestDto: ReLoginRequestDto): LoginResponseDto

    @POST("${Const.API_PATH}/auth/signup")
    suspend fun signup(@Body signupRequestDto: SignupRequestDto): LoginResponseDto

    @POST("${Const.DATA_PATH}/user/register")
    suspend fun createAsset(@Body createAssetRequestDto: CreateAssetRequestDto)
}
