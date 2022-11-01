package com.finance.android.domain.repository

import android.util.Log
import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.service.RemitService
import com.finance.android.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class RemitRepositoryImpl @Inject constructor(
    private val remitService: RemitService
) : RemitRepository {
    override suspend fun getRecommendedAccount(): Flow<Response<MutableList<RecentTradeResponseDto>>> =
        flow {
        emit(Response.Loading)
        try {
            val response = remitService.getRecommendedAccount()
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Failure(e))
        } catch (e: Exception) {
            Log.e("SOLSOL", e.stackTraceToString())
        }
    }.flowOn(Dispatchers.IO)



    override suspend fun postRemitToAccount(remitInfoRequestDto: RemitInfoRequestDto): Flow<Response<Unit>> =
        flow {
            emit(Response.Loading)
            try {
                val response = remitService.postRemitToAccount(remitInfoRequestDto)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)


    override suspend fun postRemitToPhone(remitPhoneRequestDto: RemitPhoneRequestDto): Flow<Response<Unit>> =
        flow {
            emit(Response.Loading)
            try {
                val response = remitService.postRemitToPhone(remitPhoneRequestDto)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)


    override suspend fun putRemitBookmark(acNo: String): Flow<Response<Unit>> =
        flow {
            emit(Response.Loading)
            try {
                val response = remitService.putRemitBookmark(acNo)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)

}