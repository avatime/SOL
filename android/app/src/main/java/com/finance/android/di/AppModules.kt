package com.finance.android.di

import com.finance.android.domain.DummyRepositoryImpl
import com.finance.android.domain.RetrofitClient
import com.finance.android.domain.repository.*
import com.finance.android.domain.service.BankService
import com.finance.android.domain.service.RemitService
import com.finance.android.domain.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Provides
    fun provideSampleRepository(): SampleRepository = DummyRepositoryImpl()

    @Provides
    fun provideUserRepository(
        userService: UserService
    ): UserRepository = UserRepositoryImpl(userService)

    @Provides
    fun provideRemitRepository (
        remitService: RemitService
    ) : RemitRepository = DummyRepositoryImpl()

    @Provides
    fun provideBankRepository (
        bankService: BankService
    ) : BankRepository = BankRepositoryImpl(bankService)

    @Provides
    fun provideRetrofit(): Retrofit = RetrofitClient().instance

    @Provides
    fun provideUserService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    fun provideRemitService(
        retrofit: Retrofit
    ) : RemitService = retrofit.create(RemitService::class.java)

    @Provides
    fun provideBankService(
        retrofit: Retrofit
    ) : BankService = retrofit.create(BankService::class.java)
}
