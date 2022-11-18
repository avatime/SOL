package com.finance.android.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
    val loading: MutableState<Boolean> = mutableStateOf(true)

    val error: MutableState<Exception?> = mutableStateOf(null)
    protected suspend fun <T> run(call: suspend () -> T): Flow<Response<T>> = flow {
        var flag = false

        suspend fun load() {
            loading.value = true
            error.value = null
            emit(Response.Loading)
            try {
                emit(Response.Success(call()))
                loading.value = false
            } catch (e: HttpException) {
                loading.value = false
                if (e.code() == 403 && !flag) {
                    flag = true
                    resetToken(baseRepository.reissueToken().accessToken)
                    load()
                } else {
                    error.value = e
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