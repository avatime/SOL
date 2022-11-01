package com.finance.android.domain.repository

import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow

interface SampleRepository: BaseRepository {
    fun getSampleData(): Flow<Response<Array<Int>>>
}