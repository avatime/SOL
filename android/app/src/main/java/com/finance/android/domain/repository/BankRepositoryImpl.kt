package com.finance.android.domain.repository

import android.util.Log
import com.finance.android.domain.dto.response.*
import com.finance.android.domain.service.BankService
import com.finance.android.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankRepositoryImpl @Inject constructor(
    private val bankService: BankService
) :BankRepository {
    override suspend fun checkAccount(acNo: String, cdCode: Int): Flow<Response<String>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.checkAccount(acNo, cdCode)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)


    override suspend fun getAllBankAccount(): Flow<Response<MutableList<BankInfoResponseDto>>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.getAllBankAccount()
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)
    override suspend fun getRecentAccount(): Flow<Response<MutableList<RecentTradeResponseDto>>> =
    flow {
        emit(Response.Loading)
        try {
            val response = bankService.getRecentAccount()
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Failure(e))
        } catch (e: Exception) {
            Log.e("SOLSOL", e.stackTraceToString())
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun getAllMainAccount(): Flow<Response<AccountRegisteredResponseDto>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.getAllMainAccount()
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getAccountDetail(acNo: String): Flow<Response<MutableList<BankDetailResponseDto>>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.getAccountDetail(acNo)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getAccountList(): Flow<Response<MutableList<BankAccountResponseDto>>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.getAccountList()
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getAccountSendDetail(
        acNo: String,
        type: Int
    ): Flow<Response<MutableList<BankTradeResponseDto>>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.getAccountSendDetail(acNo, type)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun putBookmarkAccount(acNo: String): Flow<Response<Unit>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.putBookmarkAccount(acNo)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun putRegisterAccount(acNo: String): Flow<Response<Unit>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.putRegisterAccount(acNo)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun putRegisterMainAccount(acNo: String): Flow<Response<Unit>> =
        flow {
            emit(Response.Loading)
            try {
                val response = bankService.putRegisterMainAccount(acNo)
                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Failure(e))
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)
}