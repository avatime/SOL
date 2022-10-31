package com.finance.android.domain.repository

import android.util.Log
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
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
): UserRepository {
    override suspend fun loadPhoneCode(): Flow<Response<String>> = flow {
        emit(Response.Loading)
        delay(2000)
        var code = ""
        repeat(6) {
            code += Random.nextInt(0, 10)
        }
        emit(Response.Success(code))
    }.flowOn(Dispatchers.IO)

    override suspend fun checkUser(checkUserRequestDto: CheckUserRequestDto): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            val response = userService.checkUser(checkUserRequestDto)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Failure(e))
        } catch (e: Exception) {
            Log.e("SOLSOL", e.stackTraceToString())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun login(loginRequestDto: LoginRequestDto): Flow<Response<LoginResponseDto>> = flow {
        emit(Response.Loading)
        try {
            val response = userService.login(loginRequestDto)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Failure(e))
        } catch (e: Exception) {
            Log.e("SOLSOL", e.stackTraceToString())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun reLogin(reLoginRequestDto: ReLoginRequestDto): Flow<Response<LoginResponseDto>> = flow {
        emit(Response.Loading)
        try {
            val response = userService.reLogin(reLoginRequestDto)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Failure(e))
        } catch (e: Exception) {
            Log.e("SOLSOL", e.stackTraceToString())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun signup(signupRequestDto: SignupRequestDto): Flow<Response<LoginResponseDto>> = flow {
        emit(Response.Loading)
        try {
            val response = userService.signup(signupRequestDto)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Failure(e))
        } catch (e: Exception) {
            Log.e("SOLSOL", e.stackTraceToString())
        }
    }.flowOn(Dispatchers.IO)
}