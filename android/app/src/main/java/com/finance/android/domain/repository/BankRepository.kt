package com.finance.android.domain.repository

import com.finance.android.domain.dto.response.*
import com.finance.android.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field

interface BankRepository {
    suspend fun getAccountList() : Flow<Response<MutableList<BankAccountResponseDto>>>
    suspend fun putRegisterAccount(@Field("ac_no")acNo:String) : Flow<Response<Unit>>
    suspend fun putRegisterMainAccount(@Field("ac_no") acNo: String) : Flow<Response<Unit>>
    suspend fun putBookmarkAccount(@Field("ac_no") acNo: String) : Flow<Response<Unit>>
    suspend fun getAccountSendDetail(@Field("an_no") acNo: String, @Field("type") type:Int) :Flow<Response<MutableList<BankTradeResponseDto>>>
    suspend fun getAccountDetail(@Field("ac_no") acNo: String) : Flow<Response<MutableList<BankDetailResponseDto>>>
    suspend fun getRecentAccount() : Flow<Response<MutableList<RecentTradeResponseDto>>>
    suspend fun checkAccount(@Field("ac_no")acNo: String, @Field("cp_code")cdCode : Int) : Flow<Response<String>>
    suspend fun getAllBankAccount() : Flow<Response<MutableList<BankInfoResponseDto>>>
    suspend fun getAllMainAccount() : Flow<Response<AccountRegisteredResponseDto>>
}