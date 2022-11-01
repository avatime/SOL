package com.finance.android.domain

import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.repository.RemitRepository
import com.finance.android.domain.dto.response.ReissueTokenResponseDto
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
    UserRepository, RemitRepository {
    override fun getSampleData(): Flow<Response<Array<Int>>> = flow {
        emit(Response.Loading)
        delay(2000)
        emit(Response.Success(arrayOf(1, 2, 3)))
    }.flowOn(Dispatchers.IO)

    override suspend fun reissueToken(): ReissueTokenResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun loadPhoneCode(): Flow<Response<String>> = flow {
        emit(Response.Loading)
        delay(2000)
        var code = ""
        repeat(6) {
            code += Random.nextInt(0, 10)
        }
        emit(Response.Success(code))
    }.flowOn(Dispatchers.IO)

    override suspend fun checkUser(checkUserRequestDto: CheckUserRequestDto): Flow<Response<Unit>> =
        flow {
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

    override suspend fun login(loginRequestDto: LoginRequestDto): Flow<Response<LoginResponseDto>> =
        flow {
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

    override suspend fun reLogin(reLoginRequestDto: ReLoginRequestDto): Flow<Response<LoginResponseDto>> =
        flow {
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

    override suspend fun signup(signupRequestDto: SignupRequestDto): Flow<Response<LoginResponseDto>> =
        flow {
            emit(
                Response.Success(LoginResponseDto("1", "1", "1", "1"))
            )
        }

    override suspend fun getRecommendedAccount(): Flow<Response<MutableList<RecentTradeResponseDto>>> =
        flow {
            emit(Response.Loading)
            //delay(2000)
            emit(
                Response.Success(
                    mutableListOf(
                        RecentTradeResponseDto(
                            acReceive = "채윤선",
                            acNo = "123456",
                            cpName = "신한은행",
                            bkStatus = true,
                            cpLogo = "https://mblogthumb-phinf.pstatic.net/20160728_194/ppanppane_1469696183585pXt1k_PNG/KB%BC%D5%C7%D8%BA%B8%C7%E8_%283%29.png?type=w800"
                        ),
                        RecentTradeResponseDto(
                            acReceive = "채윤선123123",
                            acNo = "123456",
                            cpName = "신한은행",
                            bkStatus = false,
                            cpLogo = "https://m.asunmall.kr/web/product/medium/201808/0ba1737eb6a71bf7efc6d17f51170260.jpg"
                        ),
                        RecentTradeResponseDto(
                            acReceive = "채윤선",
                            acNo = "123456",
                            cpName = "신한은행",
                            bkStatus = true,
                            cpLogo = "https://mblogthumb-phinf.pstatic.net/20160728_194/ppanppane_1469696183585pXt1k_PNG/KB%BC%D5%C7%D8%BA%B8%C7%E8_%283%29.png?type=w800"
                        ),
                        RecentTradeResponseDto(
                            acReceive = "채윤선123123",
                            acNo = "123456",
                            cpName = "신한은행",
                            bkStatus = false,
                            cpLogo = "http://m.asunmall.kr/web/product/medium/201808/0ba1737eb6a71bf7efc6d17f51170260.jpg"
                        ),
                    )
                )
            )
        }

    override suspend fun postRemitToAccount(remitInfoRequestDto: RemitInfoRequestDto): Flow<Response<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun postRemitToPhone(remitPhoneRequestDto: RemitPhoneRequestDto): Flow<Response<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun putRemitBookmark(acNo: String): Flow<Response<Unit>> {
        TODO("Not yet implemented")
    }
}
