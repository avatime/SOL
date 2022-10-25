package com.finance.backend.bank;

import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.bank.response.BankDetailRes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

interface AccountService {
    fun getAccountAll(token: String): List<BankAccountRes>
    fun registerAccount(acNo: String)
    fun registerRemitAccount(acNo: String)
    fun getAccountDetail(acNo: String): BankDetailRes
    fun registerBookmarkAccount(acNo: String, token: String)

}
