package com.finance.backend.bank;

import com.finance.backend.auth.Exceptions.TokenExpiredException
import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;
import java.util.UUID

@Service("AccountService")
class AccountServiceImpl(
        val userRepository: UserRepository,
        val accountRepository: AccountRepository,
        val jwtUtils: JwtUtils
) : AccountService {

    override fun getAccountAll(token: String): List<BankAccountRes> {
        var bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountList = accountRepository.findByUserId(userId)
            for (ac in accountList){
                bankAccountList.add(BankAccountRes(ac.acNo, ac.balance, ac.acName))
            }
        }
        return bankAccountList
    }

}
