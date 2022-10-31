package com.finance.android.domain

import com.finance.android.utils.Const
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient @Inject constructor() {
    lateinit var instance: Retrofit

    init {
        initInstance()
    }

    private fun initInstance(accessToken: String? = null) {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addNetworkInterceptor {
                val requestBuilder: Request.Builder = it.request()
                    .newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                if (accessToken != null) {
                    requestBuilder.header("access_token", accessToken)
                }
                it.proceed(requestBuilder.build())
            }
            .build()
        instance = Retrofit.Builder()
            .baseUrl(Const.WEB_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun login(accessToken: String) {
        initInstance(accessToken)
    }

    fun logout() {
        initInstance()
    }
}
