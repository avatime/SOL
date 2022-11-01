package com.finance.android.domain.service

import com.finance.android.domain.dto.response.*
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface BankService {
    @GET("bank/asset")
    suspend fun getAccountList(): MutableList<BankAccountResponseDto>

    @PUT("bank/asset")
    suspend fun putRegisterAccount(@Field("ac_no") acNo: String)

    @PUT("bank/remit")
    suspend fun putRegisterMainAccount(@Field("ac_no") acNo: String)

    @PUT("bank/bookmark")
    suspend fun putBookmarkAccount(@Field("ac_no") acNo: String)

    @GET("bank/{ac_no}/{type}")
    suspend fun getAccountSendDetail(
        @Path("ac_no") acNo: String,
        @Path("type") type: Int
    ): MutableList<BankTradeResponseDto>

    @GET("bank/all/{ac_no}")
    suspend fun getAccountDetail(@Path("ac_no") acNo: String): MutableList<BankDetailResponseDto>

    @GET("bank/recent")
    suspend fun getRecentAccount(): MutableList<RecentTradeResponseDto>

    @GET("bank/check/{ac_no}/{cp_code}")
    suspend fun checkAccount(@Path("ac_no")acNo: String, @Path("cp_code")cdCode : Int) : String

    @GET("bank/info")
    suspend fun getAllBankAccount() :MutableList<BankInfoResponseDto>

    @GET("bank/register")
    suspend fun getAllMainAccount() : AccountRegisteredResponseDto
}