package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.CheckUserRequestDto
import com.finance.android.domain.dto.request.LoginRequestDto
import com.finance.android.domain.dto.request.ReLoginRequestDto
import com.finance.android.domain.dto.request.SignupRequestDto
import com.finance.android.domain.dto.response.LoginResponseDto
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

    override suspend fun createAsset(userId: String) {
//        return userService.createAsset(userId)
    }
}
