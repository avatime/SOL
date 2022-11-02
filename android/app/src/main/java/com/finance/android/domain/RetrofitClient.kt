package com.finance.android.domain

import com.finance.android.utils.Const
import okhttp3.Interceptor
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

    private fun initInstance() {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(
                Interceptor {
                    val original: Request = it.request()
                    val request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("access_token", accessToken ?: "")
                        .header("refresh_token", refreshToken ?: "")
                        .build()
                    it.proceed(request)
                }
            )
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
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
        initInstance()
    }

    fun resetAccessToken(accessToken: String) {
        this.accessToken = accessToken
        initInstance()
    }

    fun logout() {
        initInstance()
    }
}
