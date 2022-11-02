package com.finance.android.domain.repository

import com.finance.android.domain.dto.response.*
import com.finance.android.domain.service.BankService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankRepositoryImpl @Inject constructor(
    private val bankService: BankService
) : BankRepository {
    override suspend fun getAccountList(): MutableList<BankAccountResponseDto> {
        return bankService.getAccountList()
    }

    override suspend fun putRegisterAccount(acNo: String) {
        return bankService.putRegisterAccount(acNo)
    }

    override suspend fun putRegisterMainAccount(acNo: String) {
        return bankService.putRegisterMainAccount(acNo)
    }

    override suspend fun putBookmarkAccount(acNo: String) {
        return bankService.putBookmarkAccount(acNo)
    }

    override suspend fun getAccountSendDetail(
        acNo: String,
        type: Int
    ): MutableList<BankTradeResponseDto> {
        return bankService.getAccountSendDetail(acNo, type)
    }

    override suspend fun getAccountDetail(acNo: String): MutableList<BankDetailResponseDto> {
        return bankService.getAccountDetail(acNo)
    }

    override suspend fun getRecentAccount(): MutableList<RecentTradeResponseDto> {
        return bankService.getRecentAccount()
    }

    override suspend fun checkAccount(
        acNo: String,
        cdCode: Int
    ): String {
        return bankService.checkAccount(acNo, cdCode)
    }

    override suspend fun getAllBankAccount(): MutableList<BankInfoResponseDto> {
        return bankService.getAllBankAccount()
    }

    override suspend fun getAllMainAccount(): AccountRegisteredResponseDto {
        return bankService.getAllMainAccount()
    }
}
