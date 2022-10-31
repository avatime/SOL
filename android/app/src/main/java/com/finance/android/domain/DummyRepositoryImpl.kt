package com.finance.android.domain

import com.finance.android.domain.dto.request.CheckUserRequestDto
import com.finance.android.domain.dto.request.LoginRequestDto
import com.finance.android.domain.dto.request.ReLoginRequestDto
import com.finance.android.domain.dto.request.SignupRequestDto
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.domain.repository.SampleRepository
import com.finance.android.domain.repository.UserRepository
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
class DummyRepositoryImpl @Inject constructor() :
    SampleRepository,
    UserRepository {
    override fun getSampleData(): Flow<Response<Array<Int>>> = flow {
        emit(Response.Loading)
        delay(2000)
        emit(Response.Success(arrayOf(1, 2, 3)))
    }.flowOn(Dispatchers.IO)

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
//        emit(
//            Response.Failure(
//                HttpException(
//                    retrofit2.Response.error<Unit>(
//                        409,
//                        "{\"key\":[\"somestuff\"]}"
//                            .toResponseBody("application/json".toMediaTypeOrNull())
//                    )
//                )
//            )
//        )
//        emit(
//            Response.Failure(
//                HttpException(
//                    retrofit2.Response.error<Unit>(
//                        400,
//                        "{\"key\":[\"somestuff\"]}"
//                            .toResponseBody("application/json".toMediaTypeOrNull())
//                    )
//                )
//            )
//        )
        emit(
            Response.Success(Unit)
        )
    }

    override suspend fun login(loginRequestDto: LoginRequestDto): Flow<Response<LoginResponseDto>> = flow {
        //        emit(
//            Response.Failure(
//                HttpException(
//                    retrofit2.Response.error<Unit>(
//                        401,
//                        "{\"key\":[\"somestuff\"]}"
//                            .toResponseBody("application/json".toMediaTypeOrNull())
//                    )
//                )
//            )
//        )
        emit(
            Response.Success(LoginResponseDto("1", "1", "1", "1"))
        )
    }

    override suspend fun reLogin(reLoginRequestDto: ReLoginRequestDto): Flow<Response<LoginResponseDto>> = flow {
//        emit(
//            Response.Failure(
//                HttpException(
//                    retrofit2.Response.error<Unit>(
//                        401,
//                        "{\"key\":[\"somestuff\"]}"
//                            .toResponseBody("application/json".toMediaTypeOrNull())
//                    )
//                )
//            )
//        )
        emit(
            Response.Success(LoginResponseDto("1", "1", "1", "1"))
        )
    }

    override suspend fun signup(signupRequestDto: SignupRequestDto): Flow<Response<LoginResponseDto>> = flow {
        emit(
            Response.Success(LoginResponseDto("1", "1", "1", "1"))
        )
    }
}
