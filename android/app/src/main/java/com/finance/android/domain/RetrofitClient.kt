package com.finance.android.domain

import com.finance.android.utils.Const
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: Retrofit? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null

    fun getInstance(): Retrofit {
        if (instance == null) {
            initInstance()
        }
        return instance!!
    }

    private fun initInstance(
        accessToken: String? = null,
        refreshToken: String? = null
    ) {
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
                if (refreshToken != null) {
                    requestBuilder.header("refresh_token", refreshToken)
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

    fun login(
        accessToken: String,
        refreshToken: String
    ) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        initInstance(accessToken = accessToken, refreshToken = refreshToken)
    }

    fun resetAccessToken(accessToken: String) {
        this.accessToken = accessToken
        initInstance(accessToken = accessToken, refreshToken = this.refreshToken)
    }

    fun logout() {
        initInstance()
    }
}
