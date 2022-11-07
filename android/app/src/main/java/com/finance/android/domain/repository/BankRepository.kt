package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.AccountNumberDto
import com.finance.android.domain.dto.request.CheckAccountRequestDto
import com.finance.android.domain.dto.response.*
import retrofit2.http.Body
import retrofit2.http.Field

interface BankRepository {
    suspend fun getAccountList(): MutableList<BankAccountResponseDto>
    suspend fun getRegisteredAccount(): MutableList<BankAccountResponseDto>
    suspend fun putRegisterAccount(accountNumberDtoArray: Array<AccountNumberDto>)
    suspend fun putRegisterMainAccount(accountNumberDto: AccountNumberDto)
    suspend fun putBookmarkAccount(accountNumberDto: AccountNumberDto)
    suspend fun getAccountSendDetail(
        acNo: String,
        type: Int
    ): MutableList<BankTradeResponseDto>
    suspend fun getAccountDetail(@Field("ac_no") acNo: String): MutableList<BankDetailResponseDto>
    suspend fun getRecentAccount(): MutableList<RecentTradeResponseDto>
    suspend fun checkAccount(@Body checkAccountRequestDto: CheckAccountRequestDto) : CheckAccountResponseDto
    suspend fun getAllBank(): MutableList<BankInfoResponseDto>
    suspend fun getAllMainAccount(): AccountRegisteredResponseDto
    suspend fun getAllStockCp() : MutableList<BankInfoResponseDto>
}
