package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.domain.dto.response.PushTokenRequestDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.domain.service.UserService
import com.finance.android.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {
    override suspend fun loadPhoneCode(): Flow<Response<String>> = flow {
        emit(Response.Loading)
        delay(2000)
        var code = ""
        repeat(6) {
            code += Random.nextInt(0, 10)
        }
        emit(Response.Success(code))
    }.flowOn(Dispatchers.IO)

    override suspend fun checkUser(checkUserRequestDto: CheckUserRequestDto) {
        return userService.checkUser(checkUserRequestDto)
    }

    override suspend fun login(loginRequestDto: LoginRequestDto): LoginResponseDto {
        return userService.login(loginRequestDto)
    }

    override suspend fun reLogin(reLoginRequestDto: ReLoginRequestDto): LoginResponseDto {
        return userService.reLogin(reLoginRequestDto)
    }

    override suspend fun signup(signupRequestDto: SignupRequestDto): LoginResponseDto {
        return userService.signup(signupRequestDto)
    }

    override suspend fun createAsset(createAssetRequestDto: CreateAssetRequestDto) {
        return userService.createAsset(createAssetRequestDto)
    }

    override suspend fun checkRepAccount(): Boolean {
        return userService.checkRepAccount()
    }

    override suspend fun getUserProfile(): UserProfileResponseDto {
        return userService.getUserProfile()
    }

    override suspend fun changeRepAccount(mainAccountDto: MainAccountDto) {
        return userService.changeRepAccount(mainAccountDto)
    }

    override suspend fun receivePoint(receivePointDto: ReceivePointDto) {
        return userService.receivePoint(receivePointDto)
    }

    override suspend fun putPushToken(pushTokenRequestDto: PushTokenRequestDto) {
        return userService.putPushToken(pushTokenRequestDto)
    }
}
