package com.finance.backend.bank;


import com.finance.backend.Exceptions.*
import com.finance.backend.bank.request.AccountInfoReq
import com.finance.backend.bank.response.*
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.bookmark.BookmarkRepository
import com.finance.backend.card.CardRepository
import com.finance.backend.card.response.CardInfoRes
import com.finance.backend.card.response.CardRes
import com.finance.backend.cardPaymentHistory.CardPaymentHistoryRepository
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
import com.finance.backend.util.AccountSortComparator
import org.springframework.stereotype.Service;
import java.time.LocalDateTime
import java.util.Collections
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
        private val cardPaymentHistoryRepository: CardPaymentHistoryRepository,
        private val jwtUtils: JwtUtils
) : AccountService {

    override fun getAccount(token: String): List<BankAccountRes> {
        var bankAccountList = ArrayList<BankAccountRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val userMainAccount = userRepository.findById(userId).orElse(null).account
            val accountList = accountRepository.findByUserIdAndAcType(userId, 1).orEmpty()

            for (ac in accountList){
                val corporation = corporationRepository.findById(ac.acCpCode).get()
                var acMain = 0
                if(ac.acNo == userMainAccount){
                    acMain = 1
                }
                bankAccountList.add(BankAccountRes(ac.acNo, ac.balance, ac.acName, corporation.cpName, corporation.cpLogo, ac.acReg, acMain, ac.acType))
            }
        }

        return bankAccountList
    }

    override fun getAccountAll(token: String): List<BankAccountRes> {
        var bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val userMainAccount = userRepository.findById(userId).orElse(null).account
            val accountList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 1, true,).orEmpty()
            for (ac in accountList){
                val corporation = corporationRepository.findById(ac.acCpCode).orElse(null)
                var acMain = 0
                if(ac.acNo == userMainAccount){
                    acMain = 1
                }
                bankAccountList.add(BankAccountRes(ac.acNo, ac.balance, ac.acName, corporation.cpName, corporation.cpLogo, ac.acReg, acMain, ac.acType))
            }
        }
        Collections.sort(bankAccountList, AccountSortComparator().reversed())
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

    override fun getAccountDetail(acNo: String): List<BankTradeRes> {
        var bankTradeList = ArrayList<BankTradeRes>()
        val account = accountRepository.findById(acNo).orElse(null) ?: throw NoAccountException()
        val tradeHistoryList = tradeHistoryRepository.findAllByAccountAcNoOrderByTdDtDesc(account.acNo).orEmpty()

        for (trade in tradeHistoryList){
            var balance = trade.tdVal
            if (trade.tdType == 2){ balance *= -1 }

            bankTradeList.add(BankTradeRes(trade.tdDt, balance, trade.tdCn, trade.tdType))
        }

        return bankTradeList
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
            val accountList = accountRepository.findByUserId(userId).orEmpty()

            // 북마크 한 내 계좌
            val myBookmarkList = ArrayList<String>()
            val myNotBookmarkList = ArrayList<String>()

            for (account in accountList){
                // 내가 북마크한 내 계좌
                if (bookmarkRepository.existsByUserIdAndAcNoAndBkStatus(userId, account.acNo, true)){
                    myBookmarkList.add(account.acNo)
                }else{
                    myNotBookmarkList.add(account.acNo)
                }
            }

            // 북마크 계좌 추가
            for (myBook in myBookmarkList){
                val account = accountRepository.findById(myBook).orElse(null)?: throw NoAccountException()
                val corporation = corporationRepository.findById(account.acCpCode).orElse(null)
                accountDetailList.add((RecentMyTradeRes(account.acName, myBook, corporation.cpName, true, corporation.cpLogo, corporation.cpCode)))
            }

            // 북마크 아닌 계좌 추가
            for (myBook in myNotBookmarkList){
                val account = accountRepository.findById(myBook).orElse(null)?: throw NoAccountException()
                val corporation = corporationRepository.findById(account.acCpCode).orElse(null)
                accountDetailList.add((RecentMyTradeRes(account.acName, myBook, corporation.cpName, false, corporation.cpLogo, corporation.cpCode)))
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
        val cardList = ArrayList<CardRes>()
        lateinit var insuranceList : List<MyInsuranceInfoDetailRes>
        val now = LocalDateTime.now()
        val year = now.year
        val month = now.month

        val startDate = LocalDateTime.of(year, month, 1, 0, 0, 0)
        val endDate = startDate.plusMonths(1).minusSeconds(1)

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountInfoList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 1, true).orEmpty()
            val userMainAccount = userRepository.findById(userId).orElse(null).account
            for (account in accountInfoList){
                val corporation = corporationRepository.findById(account.acCpCode).get()
                var acMain = 0
                if(account.acNo == userMainAccount){
                    acMain = 1
                }
                accountList.add(BankAccountRes(account.acNo, account.balance, account.acName, corporation.cpName, corporation.cpLogo, account.acReg, acMain, account.acType))
            }
            Collections.sort(accountList, AccountSortComparator().reversed())
            val financeInfoList = accountRepository.findByUserIdAndAcTypeAndAcReg(userId, 2, true).orEmpty()
            for (finance in financeInfoList){
                val corporation = corporationRepository.findById(finance.acCpCode).get()
                var acMain = 0
                if(finance.acNo == userMainAccount){
                    acMain = 1
                }
                financeList.add(BankAccountRes(finance.acNo, finance.balance, finance.acName, corporation.cpName, corporation.cpLogo, finance.acReg, acMain, finance.acType))
            }
            Collections.sort(financeList, AccountSortComparator().reversed())
            val cardInfoList = cardRepository.findAllByUserIdAndCdReg(userId, true).orEmpty()
            for (card in cardInfoList){
                val cardProduct = cardProductRepository.findByCdPdCode(card.cdPdCode)
                val balance = cardPaymentHistoryRepository.getByCdVal(card.cdNo,startDate,endDate)
                cardList.add(CardRes(balance ,CardInfoRes(cardProduct.cdImg, cardProduct.cdName, card.cdReg, card.cdNo)))
            }

            val insuranceInfoList = insuranceRepository.findAllByUserIdAndIsRegAndIsStatus(userId, true, 10)
            insuranceList = List(insuranceInfoList.size) {i -> insuranceInfoList[i].toEntity(isProductRepository.findById(insuranceInfoList[i].isPdCode).orElse(null)?.isPdName ?: throw NoSuchElementException())}

        }
        return AccountRegisteredRes(accountList, insuranceList, financeList, cardList)
    }

    override fun getAccountBalance(acNo: String): Long {
        return accountRepository.findByAcNo(acNo)!!.balance
    }

    override fun getMyAccountBalance(accessToken : String): Long {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            return accountRepository.findByAcNo(user.account ?: throw NoAccountException())!!.balance
        } else throw Exception()
    }
}
