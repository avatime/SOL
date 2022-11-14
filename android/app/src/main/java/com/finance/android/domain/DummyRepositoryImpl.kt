package com.finance.android.domain

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
