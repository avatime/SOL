package com.finance.backend.bank;


import com.finance.backend.Exceptions.*
import com.finance.backend.bank.request.AccountInfoReq
import com.finance.backend.bank.response.*
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.bookmark.BookmarkRepository
import com.finance.backend.card.CardRepository
import com.finance.backend.card.response.CardInfoRes
import com.finance.backend.cardProduct.CardProductRepository
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
        private val cardRepository: CardRepository,
        private val cardProductRepository: CardProductRepository,
        private val jwtUtils: JwtUtils
) : AccountService {

    override fun getAccount(token: String): List<BankAccountRes> {
        var bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountList = accountRepository.findByUserIdAndAcType(userId, 1).orEmpty()
            for (ac in accountList){
                val corporation = corporationRepository.findById(ac.acCpCode).get()
                bankAccountList.add(BankAccountRes(ac.acNo, ac.balance, ac.acName, corporation.cpName, corporation.cpLogo, ac.acReg))
            }
        }
        return bankAccountList
    }

    override fun getAccountAll(token: String): List<BankAccountRes> {
        var bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 1, true,).orEmpty()
            for (ac in accountList){
                val corporation = corporationRepository.findById(ac.acCpCode).orElse(null)
                bankAccountList.add(BankAccountRes(ac.acNo, ac.balance, ac.acName, corporation.cpName, corporation.cpLogo, ac.acReg))
            }
        }
        return bankAccountList
    }

    override fun registerAccount(acNoList: List<AccountInfoReq>) {
        for(a in acNoList){
            val account = accountRepository.findById(a.acNo).orElse(null) ?: throw NoAccountException()
            account.acreg(!account.acReg)
            accountRepository.save(account)
        }
    }

    override fun registerRemitAccount(acNo: String) {

        val account = accountRepository.findById(acNo).orElse(null) ?: throw NoAccountException()
        account.acrmreg(!account.acRmReg!!)
        accountRepository.save(account)
        val user = userRepository.findById(account.user.id).get()
        user.account(acNo)
        userRepository.save(user)
    }

    override fun registerBookmarkAccount(acNo: String, token: String) {
        val user: User
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            user = userRepository.findById(userId).orElse(null)
            if (bookmarkRepository.existsByUserIdAndAcNo(userId, acNo)){
                val bookmark = bookmarkRepository.findByUserIdAndAcNo(userId, acNo)
                bookmark.isClick()
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
        val bankAccountRes = BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo, account.acReg)
        val tradeHistoryList = tradeHistoryRepository.findAllByAccountAcNo(account.acNo).orEmpty()
        for (trade in tradeHistoryList){
            accountDetailList.add(BankTradeRes(trade.tdDt, trade.tdVal, trade.tdCn, trade.tdType))
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

    override fun getRecentTrade(token: String): List<RecentMyTradeRes> {
        var accountDetailList = ArrayList<RecentMyTradeRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))

            // 유저
            val user = userRepository.findById(userId).orElse(null)

            // 내 계좌 가져오기
            val accountList = accountRepository.findByUserId(userId)
            // 내 계좌 문자열
            val myAccountList = ArrayList<String>()
            for (account in accountList){
                myAccountList.add(account.acNo)
            }

            // 내가 북마크 한 계좌
            val bookmarkList = bookmarkRepository.findByUserId(userId).orEmpty()

            // 북마크 한 내 계좌
            val myBookmarkList = ArrayList<String>()
            val myNotBookmarkList = ArrayList<String>()
            for (bookmark in bookmarkList){
                // 내 계좌 추가
                if (myAccountList.contains(bookmark.acNo)){
                    myBookmarkList.add(bookmark.acNo)
                }
                // 상대 계좌 추가
                else {
                    myNotBookmarkList.add(bookmark.acNo)
                }
            }

            // 북마크 계좌 추가
            for (myBook in myBookmarkList){
                val account = accountRepository.findById(myBook).orElse(null)?: throw NoAccountException()
                val corporation = corporationRepository.findById(account.acCpCode).orElse(null)
                accountDetailList.add((RecentMyTradeRes(account.acName, myBook, corporation.cpName, true, corporation.cpLogo)))
            }

            // 북마크 아닌 계좌 추가
            for (myBook in myNotBookmarkList){
                val account = accountRepository.findById(myBook).orElse(null)?: throw NoAccountException()
                val corporation = corporationRepository.findById(account.acCpCode).orElse(null)
                accountDetailList.add((RecentMyTradeRes(account.acName, myBook, corporation.cpName, false, corporation.cpLogo)))
            }
        }

        return accountDetailList
    }

    override fun getUserName(acNo: String, cpCode: Long): UserRes {
        val account = accountRepository.findByAcNoAndAcCpCode(acNo, cpCode)?: let{return UserRes("")}
        val userName = userRepository.findById(account.user.id).get().name
        return UserRes(userName)
    }

    override fun getBankInfo(): List<BankInfoRes> {
        var bankInfoList = ArrayList<BankInfoRes>()
        val corporationList = corporationRepository.findTop16ByOrderByCpCode().orEmpty()
        for(corporation in corporationList){
            val bankInfo = BankInfoRes(corporation.cpName, corporation.cpLogo, corporation.cpCode)
            bankInfoList.add(bankInfo)
        }
        return bankInfoList
    }

    override fun getFinanceInfo(): List<BankInfoRes> {
        var bankInfoList = ArrayList<BankInfoRes>()
        val corporationList = corporationRepository.findTop25ByOrderByCpCodeDesc()
        for(corporation in corporationList){
            val bankInfo = BankInfoRes(corporation.cpName, corporation.cpLogo, corporation.cpCode)
            bankInfoList.add(bankInfo)
        }
        return bankInfoList

    }

    override fun getAccountRegistered(token: String): AccountRegisteredRes {
        val accountList = ArrayList<BankAccountRes>()
        val financeList = ArrayList<BankAccountRes>()
        val cardList = ArrayList<CardInfoRes>()
        lateinit var insuranceList : List<MyInsuranceInfoDetailRes>


        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountInfoList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 1, true).orEmpty()

            for (account in accountInfoList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                accountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo, account.acReg))
            }

            val financeInfoList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 2, true).orEmpty()
            for (finance in financeInfoList){
                val corporation = corporationRepository.findById(finance.acCpCode).get()
                financeList.add(BankAccountRes(finance.acNo, finance.balance, finance.acName, corporation.cpName, corporation.cpLogo, finance.acReg))
            }

            val cardInfoList = cardRepository.findAllByUserIdAndCdReg(userId, true).orEmpty()
            for (card in cardInfoList){
                println(card.cdPdCode)
                val cardProduct = cardProductRepository.findByCdPdCode(card.cdPdCode)
                cardList.add(CardInfoRes(cardProduct.cdImg, cardProduct.cdName, card.cdReg, card.cdNo))
            }

            val insuranceInfoList = insuranceRepository.findAllByUserIdAndIsRegAndIsStatus(userId, true, 10)
            insuranceList = List(insuranceInfoList.size) {i -> insuranceInfoList[i].toEntity(isProductRepository.findById(insuranceInfoList[i].isPdCode).orElse(null)?.isPdName ?: throw NoSuchElementException())}

        }
        return AccountRegisteredRes(accountList, insuranceList, financeList, cardList)
    }
}
