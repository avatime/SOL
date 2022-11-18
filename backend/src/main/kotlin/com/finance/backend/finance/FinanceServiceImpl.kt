package com.finance.backend.finance

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.bank.AccountRepository
import com.finance.backend.bank.request.AccountInfoReq
import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.cardBenefit.response.CardBenefitRes
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.corporation.response.BankInfoRes
import com.finance.backend.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service("FinanceService")
class FinanceServiceImpl(
        val corporationRepository: CorporationRepository,
        val accountRepository: AccountRepository,
        val userRepository: UserRepository,
        val jwtUtils: JwtUtils
) : FinanceService {

    override fun getFinanceAssetAll(token: String): List<BankAccountRes> {
        val bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val userMainAccount = userRepository.findById(userId).orElse(null).account
            val accountList = accountRepository.findByUserIdAndAcType(userId, 2).orEmpty()
            for(account in accountList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                var acMain = 0
                if(account.acNo == userMainAccount){
                    acMain = 1
                }
                bankAccountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo, account.acReg, acMain, account.acType))
            }
        }
        return bankAccountList
    }

    override fun getFinanceAsset(token: String): List<BankAccountRes> {
        val bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val userMainAccount = userRepository.findById(userId).orElse(null).account
            val accountList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 2, true).orEmpty()
            for(account in accountList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                var acMain = 0
                if(account.acNo == userMainAccount){
                    acMain = 1
                }
                bankAccountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo, account.acReg, acMain, account.acType))
            }
        }
        return bankAccountList
    }
    override fun putFinanceAsset(acNoList: List<AccountInfoReq>) {
        for (a in acNoList){
            val account = accountRepository.findById(a.acNo).get()
            account.acreg(!account.acReg)
            accountRepository.save(account)
        }
    }
}