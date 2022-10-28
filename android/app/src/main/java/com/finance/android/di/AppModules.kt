package com.finance.android.di

import com.finance.android.domain.DummyRepositoryImpl
import com.finance.android.domain.repository.SampleRepository
import com.finance.android.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Provides
    fun provideSampleRepository(): SampleRepository = DummyRepositoryImpl()

    @Provides
    fun provideUserRepository(): UserRepository = DummyRepositoryImpl()
}
