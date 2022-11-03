package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.AccountNumberDto
import com.finance.android.domain.dto.response.*
import retrofit2.http.Field

interface BankRepository {
    suspend fun getAccountList(): MutableList<BankAccountResponseDto>
    suspend fun getRegisteredAccount(): MutableList<BankAccountResponseDto>
    suspend fun putRegisterAccount(accountNumberDtoArray: Array<AccountNumberDto>)
    suspend fun putRegisterMainAccount(accountNumberDto: AccountNumberDto)
    suspend fun putBookmarkAccount(accountNumberDto: AccountNumberDto)
    suspend fun getAccountSendDetail(
        @Field("an_no") acNo: String,
        @Field("type") type: Int
    ): MutableList<BankTradeResponseDto>

    suspend fun getAccountDetail(@Field("ac_no") acNo: String): MutableList<BankDetailResponseDto>
    suspend fun getRecentAccount(): MutableList<RecentTradeResponseDto>
    suspend fun checkAccount(
        @Field("ac_no") acNo: String,
        @Field("cp_code") cdCode: Int
    ): String

    suspend fun getAllBank(): MutableList<BankInfoResponseDto>
    suspend fun getAllMainAccount(): AccountRegisteredResponseDto
    suspend fun getAllStockCp() : MutableList<BankInfoResponseDto>
}
