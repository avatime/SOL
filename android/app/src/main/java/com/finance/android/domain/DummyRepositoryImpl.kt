package com.finance.android.domain

import com.finance.android.domain.repository.SampleRepository
import com.finance.android.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyRepositoryImpl @Inject constructor() :
    SampleRepository {
    override fun getSampleData(): Flow<Response<Array<Int>>> = flow {
        emit(Response.Loading)
        delay(2000)
        emit(Response.Success(arrayOf(1, 2, 3)))
    }.flowOn(Dispatchers.IO)
}
