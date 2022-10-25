package com.finance.backend.bank;

import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.bank.response.BankDetailRes
import com.finance.backend.bank.response.BankTradeRes
import com.finance.backend.bank.response.RecentTradeRes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

interface AccountService {
    fun getAccountAll(token: String): List<BankAccountRes>
    fun registerAccount(acNo: String)
    fun registerRemitAccount(acNo: String)
    fun registerBookmarkAccount(acNo: String, token: String)
    fun getAccountDetail(acNo: String): BankDetailRes
    fun getAccountDetailType(acNo: String, type: Int): List<BankTradeRes>
    fun getRecentTrade(token: String): List<RecentTradeRes>
}
