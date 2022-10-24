package com.finance.backend.bank;

import com.finance.backend.bank.response.BankAccountRes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

interface AccountService {
    fun getAccountAll(token: String): List<BankAccountRes>
}
