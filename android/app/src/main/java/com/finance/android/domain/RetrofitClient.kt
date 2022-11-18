package com.finance.android.domain

import com.finance.android.utils.Const
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


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
            .addConverterFactory(getGsonConverterFactory())

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

    private fun getGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer<Any?> { json, _, _ ->
                    LocalDateTime.parse(
                        json.asString,
                        when (json.asString.length) {
                            23 -> DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                            22 -> DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS")
                            21 -> DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S")
                            else -> DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        }
                    )
                })
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonDeserializer<Any?> { json, _, _ ->
                    LocalDate.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    )
                })
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonDeserializer<Any?> { json, _, _ ->
                    LocalTime.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                    )
                })
            .create()
        return GsonConverterFactory.create(gson)
    }
}
