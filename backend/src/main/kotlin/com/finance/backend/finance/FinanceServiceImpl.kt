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

    override fun getFinanceAssetAll(token: String): List<BankAccountRes> {
        val bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountList = accountRepository.findByUserIdAndAcType(userId, 2).orEmpty()
            for(account in accountList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                bankAccountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo, account.acReg))
            }
        }
        return bankAccountList
    }

    override fun getFinanceAsset(token: String): List<BankAccountRes> {
        val bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 2, true).orEmpty()
            for(account in accountList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                bankAccountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo, account.acReg))
            }
        }
        return bankAccountList
    }
    override fun putFinanceAsset(acNoList: List<String>) {
        for (acNo in acNoList){
            val account = accountRepository.findById(acNo).get()
            account.acreg(!account.acReg)
            accountRepository.save(account)
        }
    }
}