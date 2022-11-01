package com.finance.android.domain.repository

import android.util.Log
import com.finance.android.domain.RetrofitClient
import com.finance.android.domain.dto.response.ReissueTokenResponseDto
import com.finance.android.domain.service.BaseService
import com.finance.android.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseRepositoryImpl @Inject constructor(
    private val baseService: BaseService
) : BaseRepository {
    protected suspend fun <T> run(call: suspend () -> T) = flow {
        var flag = false

        suspend fun load() {
            emit(Response.Loading)
            try {
                emit(Response.Success(call()))
            } catch (e: HttpException) {
                if (e.code() == 403 && !flag) {
                    flag = true
                    val accessToken = reissueToken().accessToken
                    RetrofitClient.resetAccessToken(accessToken)
                    load()
                } else {
                    emit(Response.Failure(e))
                }
            } catch (e: Exception) {
                Log.e("SOLSOL", e.stackTraceToString())
            }
        }

        load()
    }.flowOn(Dispatchers.IO)

    override suspend fun reissueToken(): ReissueTokenResponseDto {
        return baseService.refreshToken();
    }
}
