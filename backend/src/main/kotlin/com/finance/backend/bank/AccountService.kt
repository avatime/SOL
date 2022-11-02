package com.finance.backend.bank;

import com.finance.backend.bank.response.*
import com.finance.backend.corporation.response.BankInfoRes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

interface AccountService {
    fun getAccount(token: String): List<BankAccountRes>
    fun getAccountAll(token: String): List<BankAccountRes>
    fun registerAccount(acNoList: List<String>)
    fun registerRemitAccount(acNo: String)
    fun registerBookmarkAccount(acNo: String, token: String)
    fun getAccountDetail(acNo: String): BankDetailRes
    fun getAccountDetailType(acNo: String, type: Int): List<BankTradeRes>
    fun getRecentTrade(token: String): List<RecentMyTradeRes>
    fun getUserName(acNo: String, cpCode: Long): String
    fun getBankInfo():List<BankInfoRes>
    fun getFinanceInfo(): List<BankInfoRes>
    fun getAccountRegistered(token: String): AccountRegisteredRes
}
