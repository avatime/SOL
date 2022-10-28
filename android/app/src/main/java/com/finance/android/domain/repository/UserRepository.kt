package com.finance.android.domain.repository

import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loadPhoneCode(): Flow<Response<String>>
}