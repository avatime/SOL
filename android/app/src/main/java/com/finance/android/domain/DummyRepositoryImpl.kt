package com.finance.android.domain

import com.finance.android.domain.dto.request.CheckAccountRequestDto
import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.*
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.RemitRepository
import com.finance.android.domain.repository.SampleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyRepositoryImpl @Inject constructor() :
    SampleRepository {
    override fun getSampleData(): Array<Int> {
        return arrayOf(1, 2, 3)
    }
}
