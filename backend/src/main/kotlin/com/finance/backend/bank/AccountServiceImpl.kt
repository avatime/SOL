package com.finance.backend.bank;


import com.finance.backend.Exceptions.*
import com.finance.backend.bank.response.*
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.bookmark.BookmarkRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.corporation.response.BankInfoRes
import com.finance.backend.insurance.repository.InsuranceRepository
import com.finance.backend.insurance.repository.IsProductRepository
import com.finance.backend.insurance.response.MyInsuranceInfoDetailRes
import com.finance.backend.insurance.response.MyInsuranceInfoRes
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import org.springframework.stereotype.Service;
import java.time.LocalDateTime
import java.util.NoSuchElementException
import java.util.UUID

@Service("AccountService")
class AccountServiceImpl(
        private val userRepository: UserRepository,
        private val accountRepository: AccountRepository,
        private val tradeHistoryRepository: TradeHistoryRepository,
        private val bookmarkRepository: BookmarkRepository,
        private val corporationRepository: CorporationRepository,
        private val insuranceRepository: InsuranceRepository,
        private val isProductRepository: IsProductRepository,
        private val jwtUtils: JwtUtils
) : AccountService {

    override fun getAccountAll(token: String): List<BankAccountRes> {
        var bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val accountList = accountRepository.findByUserId(user.id).orEmpty()
            for (ac in accountList){
                val corporation = corporationRepository.findById(ac.acCpCode).get()
                bankAccountList.add(BankAccountRes(ac.acNo, ac.balance, ac.acName, corporation.cpName, corporation.cpLogo))
            }
        }
        return bankAccountList
    }

    override fun registerAccount(acNoList: List<String>) {
        for(acNo in acNoList){
            val account = accountRepository.findById(acNo).orElse(null) ?: throw NoAccountException()
            account.apply {
                acReg = !acReg!!
            }
            accountRepository.save(account)
        }
    }

    override fun registerRemitAccount(acNo: String) {

        val account = accountRepository.findById(acNo).orElse(null) ?: throw NoAccountException()
        account.apply {
            acRmReg = !acRmReg!!
        }
        accountRepository.save(account)
    }

    override fun registerBookmarkAccount(acNo: String, token: String) {
        val user: User
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            user = userRepository.findById(userId).orElse(null)
            if (bookmarkRepository.existsByUserIdAndAcNo(userId, acNo)){
                val bookmark = bookmarkRepository.findByUserIdAndAcNo(userId, acNo)
                bookmark.apply {
                    bkStatus = !bkStatus
                }
                bookmarkRepository.save(bookmark)
            }else{
                var bookmark = Bookmark(acNo, user, true)
                bookmarkRepository.save(bookmark)
            }
        }
    }

    override fun getAccountDetail(acNo: String): BankDetailRes {
        var accountDetailList = ArrayList<BankTradeRes>()
        val account = accountRepository.findById(acNo).orElse(null) ?: throw NoAccountException()
        val corporation = corporationRepository.findByCpCode(account.acCpCode)?: throw NoCorporationException()
        val bankAccountRes = BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo)
        val tradeHistoryList = tradeHistoryRepository.findAllByAccountAcNo(account.acNo).orEmpty()
        for (trade in tradeHistoryList){
            accountDetailList.add(BankTradeRes(trade.tdDt,trade.tdVal, trade.tdCn, trade.tdType))
        }
        val bankDetailRes = BankDetailRes(bankAccountRes, accountDetailList)
        return bankDetailRes
    }

    override fun getAccountDetailType(acNo: String, type: Int): List<BankTradeRes> {
        var tradeHistoryList = tradeHistoryRepository.findAllByAccountAcNoAndTdTypeOrderByTdDtDesc(acNo, type).orEmpty()
        var accountDetailList = ArrayList<BankTradeRes>()
        for (trade in tradeHistoryList){
            accountDetailList.add(BankTradeRes(trade.tdDt, trade.tdVal, trade.tdCn, trade.tdType))
        }
        return accountDetailList
    }

    override fun getRecentTrade(token: String): List<RecentTradeRes> {
        var accountDetailList = ArrayList<RecentTradeRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))

            // 북마크 계좌 추가
            val bookmarkAccountList : List<Bookmark> = bookmarkRepository.findByUserId(userId).orEmpty()
            var checkList = ArrayList<String>()
            for (bookmarkAccount in bookmarkAccountList){
                println(bookmarkAccount.acNo)
                val account : Account = accountRepository.findById(bookmarkAccount.acNo).get()
                val user : User = userRepository.findById(account.user.id).get()
                val corporation = corporationRepository.findById(account.acCpCode).get()
                accountDetailList.add(RecentTradeRes(user.name, bookmarkAccount.acNo, corporation.cpName, bookmarkAccount.bkStatus, corporation.cpLogo))
                checkList.add(bookmarkAccount.acNo)
            }

            // 최근 거래 계좌 추가
            val accountList = accountRepository.findByUserId(userId)
            for (account in accountList){
                val end = LocalDateTime.now()
                val start = end.minusMonths(3)
                val tradeHistoryList = tradeHistoryRepository.findAllByAccountAndTdDtBetween(account, start, end).orEmpty()
                for (trade in tradeHistoryList){
                    if(!checkList.contains(trade.tdTgAc)){
                        val account = accountRepository.findById(trade.tdTgAc!!).get()
                        val user : User = userRepository.findById(account.user.id).get()
                        val corporation = corporationRepository.findById(account.acCpCode).get()
                        accountDetailList.add(RecentTradeRes(user.name, account.acNo, corporation.cpName, false, corporation.cpLogo))
                        checkList.add(trade.tdTgAc!!)
                    }
                }
            }
        }

        return accountDetailList
    }

    override fun getUserName(acNo: String, cpCode: Long): String {
        val account = accountRepository.findByAcNoAndAcCpCode(acNo, cpCode)?: let{return ""}
        val userName = userRepository.findById(account.user.id).get().name
        return userName
    }

    override fun getBankInfo(): List<BankInfoRes> {
        var bankInfoList = ArrayList<BankInfoRes>()
        val corporationList = corporationRepository.findTop16ByOrderByCpCode().orEmpty()
        for(corporation in corporationList){
            val bankInfo = BankInfoRes(corporation.cpName, corporation.cpLogo)
            bankInfoList.add(bankInfo)
        }
        return bankInfoList
    }

    override fun getAccountRegistered(token: String): AccountRegisteredRes {
        val accountList = ArrayList<BankAccountRes>()
        val financeList = ArrayList<BankAccountRes>()
        lateinit var insuranceList : List<MyInsuranceInfoDetailRes>

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountInfoList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 1, true).orEmpty()
            println(accountInfoList)
            for (account in accountInfoList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                accountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo))
            }

            val financeInfoList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 2, true).orEmpty()
            for (finance in financeInfoList){
                val corporation = corporationRepository.findById(finance.acCpCode).get()
                financeList.add(BankAccountRes(finance.acNo, finance.balance, finance.acName, corporation.cpName, corporation.cpLogo))
            }

            val insuranceInfoList = insuranceRepository.findAllByUserIdAndIsRegAndIsStatus(userId, true, 10)
            insuranceList = List(insuranceInfoList.size) {i -> insuranceInfoList[i].toEntity(isProductRepository.findById(insuranceInfoList[i].isPdCode).orElse(null)?.isPdName ?: throw NoSuchElementException())}
        }

        return AccountRegisteredRes(accountList, insuranceList, financeList)
    }
}
