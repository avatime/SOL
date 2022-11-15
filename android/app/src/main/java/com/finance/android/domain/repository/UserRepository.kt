package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loadPhoneCode(): Flow<Response<String>>
    suspend fun checkUser(checkUserRequestDto: CheckUserRequestDto)
    suspend fun login(loginRequestDto: LoginRequestDto): LoginResponseDto
    suspend fun reLogin(reLoginRequestDto: ReLoginRequestDto): LoginResponseDto
    suspend fun signup(signupRequestDto: SignupRequestDto): LoginResponseDto
    suspend fun createAsset(createAssetRequestDto: CreateAssetRequestDto)
    suspend fun checkRepAccount(): Boolean
    suspend fun getUserProfile() : UserProfileResponseDto
    suspend fun changeRepAccount(mainAccountDto: MainAccountDto)
    suspend fun receivePoint(receivePointDto: ReceivePointDto)
}
