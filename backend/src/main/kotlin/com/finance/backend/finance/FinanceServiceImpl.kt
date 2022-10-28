package com.finance.backend.finance

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.bank.AccountRepository
import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.cardBenefit.response.CardBenefitRes
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.corporation.response.BankInfoRes
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service("FinanceService")
class FinanceServiceImpl(
        val corporationRepository: CorporationRepository,
        val accountRepository: AccountRepository,
        val jwtUtils: JwtUtils
) : FinanceService {

    override fun getFinanceInfo(): List<BankInfoRes> {
        var bankInfoList = ArrayList<BankInfoRes>()
        val corporationList = corporationRepository.findTop25ByOrderByCpCodeDesc()
        for(corporation in corporationList){
            val bankInfo = BankInfoRes(corporation.cpName, corporation.cpLogo)
            bankInfoList.add(bankInfo)
        }
        return bankInfoList

    }

    override fun getFinanceAsset(token: String): List<BankAccountRes> {
        val bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountList = accountRepository.findByUserIdAndAcType(userId, 2).orEmpty()
            for(account in accountList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                bankAccountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo))
            }
        }
        return bankAccountList
    }

    override fun putFinanceAsset(acNo: String) {
        val account = accountRepository.findById(acNo).get()
        account.apply {
            acReg = !acReg!!
        }
        accountRepository.save(account)
    }
}