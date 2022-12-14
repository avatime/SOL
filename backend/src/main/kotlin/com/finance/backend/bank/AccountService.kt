package com.finance.backend.bank;

import com.finance.backend.bank.request.AccountInfoReq
import com.finance.backend.bank.response.*
import com.finance.backend.corporation.response.BankInfoRes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

interface AccountService {
    fun getAccount(token: String): List<BankAccountRes>
    fun getAccountAll(token: String): List<BankAccountRes>
    fun registerAccount(acNoList: List<AccountInfoReq>)
    fun registerRemitAccount(acNo: String)
    fun registerBookmarkAccount(acNo: String, token: String)
    fun getAccountDetail(acNo: String): List<BankTradeRes>
    fun getAccountDetailType(acNo: String, type: Int): List<BankTradeRes>
    fun getRecentTrade(token: String): List<RecentMyTradeRes>
    fun getUserName(acNo: String, cpCode: Long): UserRes
    fun getBankInfo():List<BankInfoRes>
    fun getFinanceInfo(): List<BankInfoRes>
    fun getAccountRegistered(token: String): AccountRegisteredRes
    fun getAccountBalance(acNo: String): Long
    fun getMyAccountBalance(accessToken : String): Long
}
