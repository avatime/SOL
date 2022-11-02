package com.finance.android.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.finance.android.datastore.UserStore
import com.finance.android.domain.RetrofitClient
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

open class BaseViewModel(
    application: Application,
    private val baseRepository: BaseRepository
): AndroidViewModel(application) {
    protected suspend fun <T> run(call: suspend () -> T): Flow<Response<T>> = flow {
        var flag = false

        suspend fun load() {
            emit(Response.Loading)
            try {
                emit(Response.Success(call()))
            } catch (e: HttpException) {
                if (e.code() == 403 && !flag) {
                    flag = true
                    resetToken(baseRepository.reissueToken().accessToken)
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

    private suspend fun resetToken(accessToken: String) {
        RetrofitClient.resetAccessToken(accessToken)
        UserStore(getApplication()).setValue(UserStore.KEY_ACCESS_TOKEN, accessToken)
    }
}