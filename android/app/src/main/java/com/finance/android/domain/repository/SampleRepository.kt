package com.finance.android.domain.repository

interface SampleRepository: BaseRepository {
    fun getSampleData(): Array<Int>
}