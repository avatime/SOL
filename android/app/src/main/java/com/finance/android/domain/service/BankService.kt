package com.finance.android.domain.service

import com.finance.android.domain.dto.response.*
import com.finance.android.utils.Const
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface BankService {
    @GET("${Const.API_PATH}/bank/asset/all")
    suspend fun getAccountList(): MutableList<BankAccountResponseDto>

    @PUT("${Const.API_PATH}/bank/asset")
    suspend fun putRegisterAccount(@Field("ac_no") acNo: String)

    @PUT("${Const.API_PATH}/bank/remit")
    suspend fun putRegisterMainAccount(@Field("ac_no") acNo: String)

    @PUT("${Const.API_PATH}/bank/bookmark")
    suspend fun putBookmarkAccount(@Field("ac_no") acNo: String)

    @GET("${Const.API_PATH}/bank/{ac_no}/{type}")
    suspend fun getAccountSendDetail(
        @Path("ac_no") acNo: String,
        @Path("type") type: Int
    ): MutableList<BankTradeResponseDto>

    @GET("${Const.API_PATH}/bank/all/{ac_no}")
    suspend fun getAccountDetail(@Path("ac_no") acNo: String): MutableList<BankDetailResponseDto>

    @GET("${Const.API_PATH}/bank/recent")
    suspend fun getRecentAccount(): MutableList<RecentTradeResponseDto>

    @GET("${Const.API_PATH}/bank/check/{ac_no}/{cp_code}")
    suspend fun checkAccount(@Path("ac_no")acNo: String, @Path("cp_code")cdCode : Int) : String

    @GET("${Const.API_PATH}/bank/info")
    suspend fun getAllBankAccount() :MutableList<BankInfoResponseDto>

    @GET("${Const.API_PATH}/bank/register")
    suspend fun getAllMainAccount() : AccountRegisteredResponseDto
}