package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.AccountNumberDto
import com.finance.android.domain.dto.request.CheckAccountRequestDto
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

    override suspend fun getMyAccount(): MutableList<BankAccountResponseDto> {
        return bankService.getMyAccount()
    }

    override suspend fun putRegisterAccount(accountNumberDtoArray: Array<AccountNumberDto>) {
        return bankService.putRegisterAccount(accountNumberDtoArray)
    }

    override suspend fun putRegisterMainAccount(accountNumberDto: AccountNumberDto) {
        return bankService.putRegisterMainAccount(accountNumberDto)
    }

    override suspend fun putBookmarkAccount(accountNumberDto: AccountNumberDto) {
        return bankService.putBookmarkAccount(accountNumberDto)
    }

    override suspend fun getAccountSendDetail(
        acNo: String,
        type: Int
    ): MutableList<BankTradeResponseDto> {
        return bankService.getAccountSendDetail(acNo, type)
    }

    override suspend fun getAccountDetail(acNo: String): MutableList<BankTradeResponseDto> {
        return bankService.getAccountDetail(acNo)
    }

    override suspend fun getRecentAccount(): MutableList<RecentTradeResponseDto> {
        return bankService.getRecentAccount()
    }

    override suspend fun getAllBank(): MutableList<BankInfoResponseDto> {
        return bankService.getAllBank()
    }

    override suspend fun getAllMainAccount(): AccountRegisteredResponseDto {
        return bankService.getAllMainAccount()
    }

    override suspend fun getAllStockCp(): MutableList<BankInfoResponseDto> {
        return bankService.getAllStockCP()
    }

    override suspend fun checkAccount(checkAccountRequestDto: CheckAccountRequestDto): CheckAccountResponseDto {
        return bankService.checkAccount(checkAccountRequestDto.acNo,checkAccountRequestDto.cpCode)
    }

    override suspend fun getRecentMyAccount(): MutableList<RecentMyTradeResponseDto> {
        return bankService.getRecentMyAccount()
    }

    override suspend fun getAccountBalance(acNo: String): Int {
        return bankService.getAccountBalance(acNo)
    }

    override suspend fun getRepresentAccountBalance(): String {
        return bankService.getRepresentAccountBalance()
    }
}
