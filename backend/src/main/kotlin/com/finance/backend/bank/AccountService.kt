package com.finance.backend.bank;

import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.bank.response.BankDetailRes
import com.finance.backend.bank.response.BankTradeRes
import com.finance.backend.bank.response.RecentTradeRes
import com.finance.backend.corporation.response.BankInfoRes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

interface AccountService {
    fun getAccountAll(token: String): List<BankAccountRes>
    fun registerAccount(acNoList: List<String>)
    fun registerRemitAccount(acNo: String)
    fun registerBookmarkAccount(acNo: String, token: String)
    fun getAccountDetail(acNo: String): BankDetailRes
    fun getAccountDetailType(acNo: String, type: Int): List<BankTradeRes>
    fun getRecentTrade(token: String): List<RecentTradeRes>
    fun getUserName(acNo: String, cpCode: Long): String
    fun getBankInfo():List<BankInfoRes>
}
